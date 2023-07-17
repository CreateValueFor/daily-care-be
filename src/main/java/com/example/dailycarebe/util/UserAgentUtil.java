package com.example.dailycarebe.util;

import com.example.dailycarebe.core.servlet.ServletUtil;
import eu.bitwalker.useragentutils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserAgentUtil {
    public static String getUserAgentString(HttpServletRequest request) {
        if (request == null)
            return null;
        return request.getHeader("User-Agent");
    }

    public static String getUserAgentString() {
        return getUserAgentString(ServletUtil.getCurrentRequest());
    }

    public static UserAgent getUserAgent(HttpServletRequest request) {
        try {
            String userAgentString = getUserAgentString(request);
            return UserAgent.parseUserAgentString(userAgentString);
        } catch (Exception ignored) {}
        return null;
    }

    public static UserAgent getUserAgent() {
        return getUserAgent(ServletUtil.getCurrentRequest());
    }

    public static OperatingSystem getUserOs(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        return userAgent == null ? OperatingSystem.UNKNOWN : userAgent.getOperatingSystem();
    }

    public static OperatingSystem getUserOs() {
        return getUserOs(ServletUtil.getCurrentRequest());
    }

    public static Browser getBrowser(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        return userAgent == null ? Browser.UNKNOWN : userAgent.getBrowser();
    }

    public static Browser getBrowser() {
        return getBrowser(ServletUtil.getCurrentRequest());
    }

    public static Version getBrowserVersion(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        return userAgent == null ? new Version("0", "0", "0") : userAgent.getBrowserVersion();
    }

    public static BrowserType getBrowserType(HttpServletRequest request) {
        Browser browser = getBrowser(request);
        return browser == null ? BrowserType.UNKNOWN : browser.getBrowserType();
    }

    public static BrowserType getBrowserType() {
        return getBrowserType(ServletUtil.getCurrentRequest());
    }

    public static RenderingEngine getRenderingEngine(HttpServletRequest request) {
        Browser browser = getBrowser(request);
        return browser == null ? RenderingEngine.OTHER : browser.getRenderingEngine();
    }

    public static RenderingEngine getRenderingEngine() {
        return getRenderingEngine(ServletUtil.getCurrentRequest());
    }

    public static Version getBrowserVersion() {
        return getBrowserVersion(ServletUtil.getCurrentRequest());
    }

    public static DeviceType getDeviceType(HttpServletRequest request) {
        OperatingSystem operatingSystem = getUserOs(request);
        return operatingSystem == null ? DeviceType.UNKNOWN : operatingSystem.getDeviceType();
    }

    public static DeviceType getDeviceType() {
        return getDeviceType(ServletUtil.getCurrentRequest());
    }

    public static Manufacturer getManufacturer(HttpServletRequest request) {
        OperatingSystem operatingSystem = getUserOs(request);
        return operatingSystem == null ? Manufacturer.OTHER : operatingSystem.getManufacturer();
    }

    public static Manufacturer getManufacturer() {
        return getManufacturer(ServletUtil.getCurrentRequest());
    }

    public static Map<String, String> getAgentDetail(HttpServletRequest request) {
        Map<String, String> agentDetail = new HashMap<>();

        Optional.ofNullable(getBrowser(request))
            .ifPresent(o -> agentDetail.put("browser", o.toString()));
        Optional.ofNullable(getBrowserType(request))
            .ifPresent(o -> agentDetail.put("browserType", o.toString()));
        Optional.ofNullable(getBrowserVersion(request))
            .ifPresent(o -> agentDetail.put("browserVersion", o.toString()));
        Optional.ofNullable(getRenderingEngine(request))
            .ifPresent(o -> agentDetail.put("renderingEngine", o.toString()));
        Optional.ofNullable(getUserOs(request))
            .ifPresent(o -> agentDetail.put("os", o.toString()));
        Optional.ofNullable(getDeviceType(request))
            .ifPresent(o -> agentDetail.put("deviceType", o.toString()));
        Optional.ofNullable(getManufacturer(request))
            .ifPresent(o -> agentDetail.put("manufacturer", o.toString()));

        return agentDetail;
    }
}
