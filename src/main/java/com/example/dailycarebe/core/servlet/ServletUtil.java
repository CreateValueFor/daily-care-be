package com.example.dailycarebe.core.servlet;

import com.example.dailycarebe.util.UserAgentUtil;
import eu.bitwalker.useragentutils.Browser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ServletUtil {
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
        if (ip == null)
            return "";
        String[] ips = ip.split(",");
        if (ips.length > 0) {
            ip = ips[0];
        }
        return ip.trim();
    }

    public static HttpServletRequest getCurrentRequest() {
        try {
            HttpServletRequest request =
                    (HttpServletRequest) RequestContextHolder.currentRequestAttributes().getAttribute(
                            MultiReadableHttpServletRequest.class.getSimpleName()
                            , RequestAttributes.SCOPE_REQUEST
                    );

            if (request == null)
                request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            return request;
        } catch (Exception e) {}
        return null;
    }

    public static HttpServletResponse getCurrentResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getJsonContentType(HttpServletRequest request) {
        Browser browser = UserAgentUtil.getBrowser(request);

        if (browser == Browser.IE) {
            return "text/plain; charset=UTF-8";
        }

        return "application/json; charset=UTF-8";
    }
}
