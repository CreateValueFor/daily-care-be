package com.example.dailycarebe.util;

import com.example.dailycarebe.auth.authorization.dto.AccessControlScope;
import com.example.dailycarebe.auth.authentication.AppUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
public class SecurityContextUtil {
  public static final String CONTEXT_KEY_MODEL_LOCK_VALIDATION_DISABLE = "MODEL_LOCK_VALIDATION_DISABLE";
  public static final String CONTEXT_KEY_ENTITY_SECURITY_VALIDATION_DISABLE = "CONTEXT_KEY_ENTITY_SECURITY_VALIDATION_DISABLE";

  public static final long ANONYMOUS_USER_ID = -1L;
  public static final String ANONYMOUS = HashidsUtil.encodeNumber(ANONYMOUS_USER_ID);
  public static final long SYSTEM_USER_ID = 0L;
  public static final String SYSTEM = HashidsUtil.encodeNumber(SYSTEM_USER_ID);

  public static String getUserUuid() {
    return getCurrentUserDetails()
      .map(AppUserDetails::getUserUuid)
      .orElse(ANONYMOUS);
  }

  public static long getUserId() {
    return getCurrentUserDetails()
      .map(AppUserDetails::getUserId)
      .orElse(ANONYMOUS_USER_ID);
  }

  public static String getUserName() {
    return getCurrentUserDetails()
      .map(AppUserDetails::getUsername)
      .orElse(ANONYMOUS);
  }
  public static Optional<AppUserDetails> getCurrentUserDetails() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof AppUserDetails) {
      return Optional.of((AppUserDetails) authentication.getPrincipal());
    }
    return Optional.empty();
  }
  public static Set<String> getUserRoles() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    Set<String> roles = new HashSet<>();

    if (authentication != null) {
      authentication.getAuthorities()
        .forEach(e -> roles.add(e.getAuthority()));
    }
    return roles;
  }
  public static Map<String, Set<AccessControlScope>> mapToCustomerRoleDto(Set<String> roles) {
    return getCurrentUserDetails()
      .map(u -> {
        Map<String, Set<AccessControlScope>> accessScopes = u.getAccessScopes();
        return roles.stream()
          .collect(Collectors.toMap(roleId -> roleId, roleId -> accessScopes.get(roleId) == null ? new HashSet<AccessControlScope>() : accessScopes.get(roleId)));
      }).orElse(null);
  }

  private static boolean isJwtAuthenticated() {
    return getCurrentUserDetails().isPresent();
  }

  public static Set<String> getUserMatchedRoles(HashSet<String> roles) {
    Set<String> userRoles = getUserRoles();
    return userRoles.stream().filter(roles::contains).collect(Collectors.toSet());
  }

  public static Map<String, Set<AccessControlScope>> getUserMatchedCustomerRoles(Set<String> roles) {
    Set<String> userRoles = getUserRoles();
    roles = roles.stream().filter(userRoles::contains).collect(Collectors.toSet());
    return mapToCustomerRoleDto(roles);
  }

  public static boolean hasAnyRole(Set<String> roles) {
    Set<String> contextUserRoles = getUserRoles();
    return contextUserRoles.stream().anyMatch(roles::contains);
  }
  public static boolean hasRoles(Set<String> roles){
    Set<String> contextUserRoles = getUserRoles();
    return contextUserRoles.containsAll(roles);
  }

  public static boolean hasRole(String role) {
    Set<String> contextUserRoles = getUserRoles();
    return contextUserRoles.stream().anyMatch(role::equals);
  }


  public static boolean isMe(Long userIdOfEntity) {
    return userIdOfEntity != null && getUserId() == userIdOfEntity;
  }

  public static <T> T getCustomContextProperty(String key, Class<T> clazz) {
    if (RequestContextHolder.getRequestAttributes() == null)
      return null;

    try {
      return (T) RequestContextHolder.currentRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    } catch (IllegalStateException e) {
      return null;
    }
  }

  public static void setCustomContextProperty(String key, Object value) {
    if (RequestContextHolder.getRequestAttributes() == null)
      return;
    RequestContextHolder.currentRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
  }

  public static void removeCustomContextProperty(String key) {
    if (RequestContextHolder.getRequestAttributes() == null)
      return;
    RequestContextHolder.currentRequestAttributes().removeAttribute(key, RequestAttributes.SCOPE_REQUEST);
  }

  public static void clearSecurityContext() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }


}
