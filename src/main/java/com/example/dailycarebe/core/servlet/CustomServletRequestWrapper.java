package com.example.dailycarebe.core.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class CustomServletRequestWrapper {
    private HttpServletRequest request;

    private CustomServletRequestWrapper(HttpServletRequest request) {
        this.request = request;
    }

    public static CustomServletRequestWrapper of(HttpServletRequest request) {
        return new CustomServletRequestWrapper(request);
    }

    public static CustomServletRequestWrapper of(ServletRequest request) {
        return of((HttpServletRequest) request);
    }

    public Map<String, String> headerMap() {
        Map<String, String> convertedHeaderMap = new HashMap<>();

        Enumeration<String> headerMap = request.getHeaderNames();

        while (headerMap.hasMoreElements()) {
            String name = headerMap.nextElement();
            String value = request.getHeader(name);

            convertedHeaderMap.put(name, value);
        }
        return convertedHeaderMap;
    }

    public Map<String, String> parameterMap() {
        Map<String, String> convertedParameterMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();

        for (String key : parameterMap.keySet()) {
            String[] values = parameterMap.get(key);
            StringJoiner valueString = new StringJoiner(",");

            for (String value : values) {
                valueString.add(value);
            }

            convertedParameterMap.put(key, valueString.toString());
        }
        return convertedParameterMap;
    }

    public String getRequestUri() {
        return request.getRequestURI();
    }

    public String getRequestMethod() {
        return request.getMethod();
    }

    public String getRequestBody() {
        try {
            return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            return "Error while reading request";
        }
    }
}
