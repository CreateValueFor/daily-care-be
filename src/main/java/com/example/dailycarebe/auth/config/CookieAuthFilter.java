package com.example.dailycarebe.auth.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

//@Component
public class CookieAuthFilter extends GenericFilterBean  {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//    boolean result = sessionComponent.validateAtomSession((HttpServletRequest) request);
//    if(!result)
//    {
//      chain.doFilter(request, response);
//      return;
//    }
    CookieAuthenticationToken cookie = null;
    Map<String, Object> map = null;
//    try
//    {
//      map = sessionComponent.getUserInfoFromCookie((HttpServletRequest) request);
//    }
//    catch
//    (InvalidKeyException | NumberFormatException | NoSuchAlgorithmException | NoSuchPaddingException
//     | InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException
//     | BadPaddingException e)
//    {
//      chain.doFilter(request, response);
//      return;
//    }
    cookie = new CookieAuthenticationToken(map, true);
    SecurityContextHolder.getContext().setAuthentication(cookie);
    chain.doFilter(request, response);
  }
}
