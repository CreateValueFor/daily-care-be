package com.example.dailycarebe.error;

import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.core.servlet.ServletUtil;
import com.example.dailycarebe.error.model.ErrorLog;
import com.example.dailycarebe.error.repository.ErrorLogRepository;
import com.example.dailycarebe.util.MdcUtil;
import com.example.dailycarebe.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.example.dailycarebe.util.MdcUtil.*;


@Service
@RequiredArgsConstructor
public class ErrorLogService extends BaseService<ErrorLog, ErrorLogRepository> {

    private final ErrorLogRepository errorLogRepository;
//    private final SlackService slackService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeErrorLog(String application, String message) {
        writeErrorLog(application, message, null, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeErrorLogWithDefualtApplicationName(String message, String log) {
        writeErrorLog(getApplicationName(), message, log, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeErrorLog(String application, String message, String log) {
        writeErrorLog(application, message, log, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeErrorLog(String application, String message, String log, String additionalContextInfo) {
        if (isLocalEnv())
            return;

        HttpServletRequest servletRequest = ServletUtil.getCurrentRequest();
        if (servletRequest != null
                && (HttpMethod.POST.matches(servletRequest.getMethod()) || HttpMethod.PUT.matches(servletRequest.getMethod()))) {
            if (!StringUtils.startsWithIgnoreCase(servletRequest.getContentType(), "multipart/")) {
                try {
                    MdcUtil.set(MdcUtil.BODY_MAP_MDC, IOUtils.toString(servletRequest.getReader()));
                } catch (Exception ignored) {}
            }
        }

        Map<String, String> mdcContextMap = MdcUtil.getCopyOfContext();

        ErrorLog.ErrorLogBuilder errorLogBuilder = ErrorLog.builder()
                .userId(SecurityContextUtil.getUserId())
                .app(application)
                .message(message)
                .log(log)
                .context(additionalContextInfo);

        if (mdcContextMap != null) {
          errorLogBuilder = errorLogBuilder.requestUri(mdcContextMap.get(REQUEST_URI_MDC))
            .requestMethod(mdcContextMap.get(REQUEST_METHOD_MDC))
            .userAgentDetail(mdcContextMap.get(USER_AGENT_DETAIL_MDC))
            .header(mdcContextMap.get(HEADER_MAP_MDC))
            .queryParam(mdcContextMap.get(QUERY_PARAMETER_MAP_MDC))
            .body(mdcContextMap.get(BODY_MAP_MDC))
            .userInfo(mdcContextMap.get(USER_INFO_MDC));

        }

        ErrorLog errorLog = errorLogRepository.save(errorLogBuilder.build());
        String title = errorLog.getApp() + ", U=" + errorLog.getUserId() + " - " + errorLog.getRequestMethod() + " " + errorLog.getRequestUri() + " " + errorLog.getMessage();
//        slackService.error(title, errorLog.getLog());
    }
}
