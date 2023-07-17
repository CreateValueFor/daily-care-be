package com.example.dailycarebe.auth.config;

import com.example.dailycarebe.auth.authentication.AppUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CookieAuthenticationToken extends AbstractAuthenticationToken {
  private Object credentials;
  private static final long serialVersionUID = -5333188013042870764L;

  public CookieAuthenticationToken(){
    super(null);
  }

  public CookieAuthenticationToken(Map<String, Object> map, boolean isAuthentication){
    super(new ArrayList<GrantedAuthority>());
    AppUserDetails userDetails = new AppUserDetails();
    userDetails.setLoginId(String.valueOf(map.get("userId")));
    setDetails(userDetails);
    this.credentials = null;
    setAuthenticated(isAuthentication);
  }

  public CookieAuthenticationToken(Collection<? extends GrantedAuthority> authorities)
  {
    super(authorities);
  }

  @Override
  public Object getCredentials()
  {
    return credentials;
  }

  @Override
  public Object getPrincipal()
  {
    return ((AppUserDetails)getDetails()).getUsername();
  }
}
