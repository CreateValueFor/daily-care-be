package com.example.dailycarebe.auth.authentication;

import com.example.dailycarebe.auth.authentication.model.AppUserDetailsType;
import com.example.dailycarebe.auth.authorization.dto.AccessControlScope;
import com.example.dailycarebe.auth.authorization.dto.RoleWithScope;
import com.example.dailycarebe.util.HashidsUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class AppUserDetails implements UserDetails, OAuth2AuthenticatedPrincipal {
  private AppUserDetailsType type;

  private String loginId;
  private String userUuid;

  private String password;

  private String email;

  private final Set<RoleWithScope> roles = new HashSet<>();

  private Collection<GrantedAuthority> authorities = Sets.newHashSet();

  private final Map<String, Set<AccessControlScope>> accessScopes = new HashMap<>();

  private String name;

  private boolean isAccountNonExpired = true;

  private boolean isAccountNonLocked = true;

  private boolean isCredentialsNonExpired = true;

  private boolean isEnabled = true;

  private long userId;

  private final Map<String, Object> attributes;

  public AppUserDetails() {
    this.attributes = new HashMap<>();
  }

  public AppUserDetails(Map<String, Object> attributes, Collection<GrantedAuthority> authorities) {
    Assert.notEmpty(attributes, "attributes cannot be empty");
    this.attributes = Collections.unmodifiableMap(attributes);
    this.authorities = (authorities != null) ? Collections.unmodifiableCollection(authorities)
      : AuthorityUtils.NO_AUTHORITIES;
    this.name = (name != null) ? name : (String) this.attributes.get("sub");
  }

  public AppUserDetails setRoles(Set<RoleWithScope> roles) {
    this.roles.clear();
    if (roles != null && !roles.isEmpty()) {
      this.roles.addAll(roles);
    }
    adjust();
    return this;
  }

  @JsonIgnore
  public void adjust() {
    this.authorities.clear();
    this.accessScopes.clear();
    if (!roles.isEmpty()) {
      this.authorities.addAll(mapCustomerRolesToAuthorities(roles));
      this.accessScopes.putAll(mapCustomerRolesToAccessScopesMap(roles));
    }
  }

  public AppUserDetails setUserUuid(String userUuid) {
    this.userUuid = userUuid;
    this.userId = HashidsUtil.decodeNumber(userUuid);
    return this;
  }

  public AppUserDetails setUserId(long userId) {
    this.userId = userId;
    this.userUuid = HashidsUtil.encodeNumber(userId);
    return this;
  }

  private Set<SimpleGrantedAuthority> mapCustomerRolesToAuthorities(Set<RoleWithScope> roleWithScopes) {
    return roleWithScopes.stream()
      .map(role -> new SimpleGrantedAuthority(role.getRoleId()))
      .collect(Collectors.toSet());
  }

  private Map<String, Set<AccessControlScope>> mapCustomerRolesToAccessScopesMap(Set<RoleWithScope> roleWithScopes) {
    return roleWithScopes
      .stream()
      .filter(role -> !CollectionUtils.isEmpty(role.getAccessControlScopes()))
      .collect(Collectors.toMap(RoleWithScope::getRoleId, RoleWithScope::getAccessControlScopes));
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getUsername() {
    return getLoginId();
  }
}
