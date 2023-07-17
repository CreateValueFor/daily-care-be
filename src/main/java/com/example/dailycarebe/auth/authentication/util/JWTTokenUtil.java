package com.example.dailycarebe.auth.authentication.util;

import com.example.dailycarebe.auth.authentication.AppUserDetails;
import com.example.dailycarebe.auth.authentication.dto.JWTUser;
import com.example.dailycarebe.auth.authentication.model.AppUserDetailsType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
/**
 * Used for managing token based authentication for customer and user
 *
 * @author c.samson
 */
@Component
public class JWTTokenUtil implements Serializable {

  static final int GRACE_PERIOD = 200;
  static final String CLAIM_KEY_USERNAME = "sub";
  static final String CLAIM_KEY_AUDIENCE = "aud";
  static final String CLAIM_KEY_CREATED = "iat";
  static final String AUDIENCE_UNKNOWN = "unknown";
  static final String AUDIENCE_API = "api";
  static final String AUDIENCE_WEB = "web";
  static final String AUDIENCE_MOBILE = "mobile";
  static final String AUDIENCE_TABLET = "tablet";

//  @Value("${jwt.secret}")
  public static String JWT_SECRET_KEY = "0123456789-0123456789-0123456789";

  //  @Value("${jwt.expiration}")
  private Long expiration = 1000_000_000L;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public String getAudienceFromToken(String token) {
    return getClaimFromToken(token, Claims::getAudience);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
      .setSigningKey(getBase64JwtSecretKey())
      .parseClaimsJws(token)
      .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(getDate());
  }

  private Date getDate() {
    return new Date(new Date().getTime());
  }

  private Boolean isTokenExpiredWithGrace(String token) {
    Date expiration = getExpirationDateFromToken(token);
    expiration = addSeconds(expiration, GRACE_PERIOD);
    return expiration.before(getDate());
  }

  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  private Boolean isCreatedBeforeLastPasswordResetWithGrace(Date created, Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(addSeconds(lastPasswordReset, GRACE_PERIOD)));
  }

  private Date addSeconds(Date date, Integer seconds) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.SECOND, seconds);
    return cal.getTime();
  }

  private String generateAudience() {
    return AUDIENCE_API;
  }

  private Boolean ignoreTokenExpiration(String token) {
    String audience = getAudienceFromToken(token);
    return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
  }

  public String generateToken(AppUserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    AppUserDetailsType type = userDetails.getType();
    claims.put("type", type.name());
    claims.put("userUuid", userDetails.getUserUuid());
    return doGenerateToken(claims, userDetails.getUsername(), generateAudience());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject, String audience) {
    final Date createdDate = getDate();
    final Date expirationDate = calculateExpirationDate(createdDate);

    return Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setAudience(audience)
      .setIssuedAt(createdDate)
      .setExpiration(expirationDate)
      .signWith(SignatureAlgorithm.HS256, getBase64JwtSecretKey())
      .compact();
  }

  public Boolean canTokenBeRefreshedWithGrace(String token, Date lastPasswordReset) {
    final Date created = getIssuedAtDateFromToken(token);
    boolean t = isCreatedBeforeLastPasswordResetWithGrace(created, lastPasswordReset);
    boolean u = isTokenExpiredWithGrace(token);
    boolean v = ignoreTokenExpiration(token);
    System.out.println(t + " " + u + " " + v);
    System.out.println(!isCreatedBeforeLastPasswordResetWithGrace(created, lastPasswordReset)
      && (!isTokenExpiredWithGrace(token) || ignoreTokenExpiration(token)));
    //return !isCreatedBeforeLastPasswordResetWithGrace(created, lastPasswordReset)
    //        && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    return true;
  }

  public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
    final Date created = getIssuedAtDateFromToken(token);
    return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
      && (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  public String refreshToken(String token) {
    final Date createdDate = getDate();
    final Date expirationDate = calculateExpirationDate(createdDate);

    final Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(createdDate);
    claims.setExpiration(expirationDate);

    return Jwts.builder()
      .setClaims(claims)
      .signWith(SignatureAlgorithm.HS256, getBase64JwtSecretKey())
      .compact();
  }

  private String getBase64JwtSecretKey() {
    return Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    JWTUser user = (JWTUser) userDetails;
    final String username = getUsernameFromToken(token);
    final Date created = getIssuedAtDateFromToken(token);
    //final LocalDate expiration = getExpirationDateFromToken(token);

    boolean usernameEquals = username.equals(user.getUsername());
    boolean isTokenExpired = isTokenExpired(token);
    boolean isTokenCreatedBeforeLastPasswordReset = isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());

    return (

      usernameEquals && !isTokenExpired && !isTokenCreatedBeforeLastPasswordReset
    );
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 1000);
  }

}
