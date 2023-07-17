package com.example.dailycarebe.rest;

import com.example.dailycarebe.error.ErrorLogService;
import com.example.dailycarebe.exception.*;
import com.example.dailycarebe.util.MdcUtil;
import com.example.dailycarebe.util.SecurityContextUtil;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
  private static Map<Class, Function<Throwable, ResponseEntity>> nestedExceptionFuncMap = new LinkedHashMap<>();

  public static final String RESPONSE_MSG_NOT_EXISTS_KEY = "존재하지 않는 데이터 입니다.";
  public static final String RESPONSE_MSG_ALREADY_EXIST = "이미 존재하는 데이터를 중복하여 생성할 수 없습니다.";
  public static final String RESPONSE_MSG_INVALID_CLIENT = "허용되지 않는 접근 입니다.";
  public static final String RESPONSE_MSG_INVALID_REQUEST_DATA_FORMAT = "잘못된 요청 데이터 형식 입니다.";
  public static final String RESPONSE_MSG_ACCESS_LOCKED_RESOURCE = "동일한 요청이 이미 처리중 입니다. 잠시 후 다시 시도해 주세요.";

  @Autowired
  private ErrorLogService errorLogService;

  //    @Value("${spring.application.name}")
  private String applicationName = "dailyCare";

  @PostConstruct
  public void init() {
    nestedExceptionFuncMap.put(InvalidKeyException.class, this::getCommonBadRequestResponseEntity);
//        nestedExceptionFuncMap.put(RetryLaterException.class, this::getCommonBadRequestResponseEntity);
//        nestedExceptionFuncMap.put(InvalidRequestException.class, this::getCommonBadRequestResponseEntity);
    nestedExceptionFuncMap.put(InvalidDataRequestException.class, this::getCommonBadRequestResponseEntity);
//        nestedExceptionFuncMap.put(UserNotActivatedException.class, this::getCommonBadRequestResponseEntity);

    nestedExceptionFuncMap.put(NotExistKeyException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_NOT_EXISTS_KEY));
    nestedExceptionFuncMap.put(EmptyResultDataAccessException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_NOT_EXISTS_KEY, t));

    nestedExceptionFuncMap.put(DuplicatedKeyException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_ALREADY_EXIST));

//        nestedExceptionFuncMap.put(NoSuchClientException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_INVALID_CLIENT, t));
//        nestedExceptionFuncMap.put(InvalidClientException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_INVALID_CLIENT, t));

    nestedExceptionFuncMap.put(HttpMessageNotReadableException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_INVALID_REQUEST_DATA_FORMAT));

//        nestedExceptionFuncMap.put(LockNotAvailableException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_ACCESS_LOCKED_RESOURCE, t));
//        nestedExceptionFuncMap.put(DistributedLockException.class, t -> getCommonBadRequestResponseEntity(RESPONSE_MSG_ACCESS_LOCKED_RESOURCE, t));
  }

  @ExceptionHandler(ConcurrencyFailureException.class)
  @ResponseBody
  public ResponseEntity onException(ConcurrencyFailureException ex) {
    return createErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity onException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<String> fieldErrorMessages = new ArrayList<>();
    for (FieldError fieldError : result.getFieldErrors()) {
      String message = fieldError.getDefaultMessage();
//            String rejectedValue = fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();
//            if (!StringUtils.isEmpty(rejectedValue)) {
//                message += " '" + rejectedValue + "'";
//            }
      fieldErrorMessages.add(message);
    }
    return getCommonBadRequestResponseEntity(fieldErrorMessages.isEmpty() ? ex.getMessage() : String.join("\n", fieldErrorMessages));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseBody
  public ResponseEntity onException(MethodArgumentTypeMismatchException ex) {
    return getCommonBadRequestResponseEntity(String.format("유효하지 않는 데이터 형식 입니다. '%s'", ex.getValue()));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseBody
  public ResponseEntity onException(MissingServletRequestParameterException ex) {
    return getCommonBadRequestResponseEntity(String.format("'%s'는 필수 파라미터 값입니다.", ex.getParameterName()));
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseBody
  public ResponseEntity onException(BadCredentialsException e) {
    return createErrorResponseEntity(HttpStatus.valueOf(401), "계정 정보가 일치하지 않습니다.");
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  public ResponseEntity onException(AccessDeniedException e) {
    return createErrorResponseEntity(HttpStatus.FORBIDDEN, "권한이 없는 요청 입니다.");
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseBody
  public ResponseEntity onException(HttpRequestMethodNotSupportedException e) {
    return createErrorResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 접근 입니다.");
  }

  @ExceptionHandler(HttpMediaTypeException.class)
  @ResponseBody
  public ResponseEntity onException(HttpMediaTypeException ex) {
    return getCommonBadRequestResponseEntity(ex);
  }

//    @ExceptionHandler({OAuth2Exception.class, ClientAuthenticationException.class})
//    @ResponseBody
//    public ResponseEntity onException(OAuth2Exception ex) {
//        return createErrorResponseEntity(HttpStatus.valueOf(ex.getHttpErrorCode()), ex.getMessage());
//    }
//
//    @ExceptionHandler({InvalidGrantException.class})
//    @ResponseBody
//    public ResponseEntity onException(InvalidGrantException ex) {
//        return createErrorResponseEntity(HttpStatus.BAD_REQUEST, "존재하지 않는 계정 정보 입니다.");
//    }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseBody
  public ResponseEntity onException(ResourceNotFoundException ex) {
    return createErrorResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ResponseEntity onException(ConstraintViolationException ex) {
    return getCommonBadRequestResponseEntity(ex);
  }

  @ExceptionHandler(ClientAbortException.class)
  @ResponseBody
  public Object onException(ClientAbortException ex) {
    if (SecurityContextUtil.getUserId() == SecurityContextUtil.ANONYMOUS_USER_ID
      && "/".equals(MdcUtil.get(MdcUtil.REQUEST_URI_MDC))
    ) {
      return createErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
    return onException((Exception) ex);
  }
//
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    @ResponseBody
//    public ResponseEntity onException(MaxUploadSizeExceededException ex) {
//        return createErrorResponseEntity(HttpStatus.BAD_REQUEST, MAX_FILE_SIZE + "Mb 까지만 업로드 가능합니다.");
//    }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Object onException(Exception ex) {
    log.error("Unexpected Error", ex);

    Optional<ResponseEntity> nestedResponseEntity = findNestedException(ex);
    if (nestedResponseEntity.isPresent()) {
      return nestedResponseEntity.get();
    }

    writeExceptionToErrorLog(ex);

    return remapResponseEntityOnException(ex);
  }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Object onException(Exception ex) {
//        log.error("Unexpected Error", ex);
//
//        Optional<ResponseEntity> nestedResponseEntity = findNestedException(ex);
//        if (nestedResponseEntity.isPresent()) {
//            return nestedResponseEntity.get();
//        }
//
//        writeExceptionToErrorLog(ex);
//
//        return remapResponseEntityOnException(ex);
//    }

  private Optional<ResponseEntity> findNestedException(Exception ex) {
    List<Throwable> throwableList = ExceptionUtils.getThrowableList(ex);
    return throwableList.stream()
      .filter(e -> nestedExceptionFuncMap.keySet().stream().anyMatch(f -> f.isAssignableFrom(e.getClass())))
      .findAny()
      .map(e -> nestedExceptionFuncMap.get(e.getClass()).apply(e));
  }

  public static ResponseEntity remapResponseEntityOnException(Exception ex) {
    ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
    if (responseStatus != null) {
      return createErrorResponseEntity(responseStatus.value(), responseStatus.reason());
    } else {
      return createErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
  }

  private void writeExceptionToErrorLog(Throwable ex) {
    StringWriter sw = new StringWriter();
    ex.printStackTrace(new PrintWriter(sw));
    errorLogService.writeErrorLog(applicationName, ex.getMessage(), Throwables.getStackTraceAsString(ex));
  }

  private ResponseEntity getCommonBadRequestResponseEntity(Throwable ex) {
    return createErrorResponseEntity(HttpStatus.BAD_REQUEST, ex);
  }

  private ResponseEntity getCommonBadRequestResponseEntity(String message) {
    return createErrorResponseEntity(HttpStatus.BAD_REQUEST, message);
  }

  private ResponseEntity getCommonBadRequestResponseEntity(String message, Throwable ex) {
    return createErrorResponseEntity(HttpStatus.BAD_REQUEST, message, ex);
  }

  private ResponseEntity getCommonInternalErrorResponseEntity(Throwable ex) {
    return createErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  private static ResponseEntity createErrorResponseEntity(HttpStatus httpStatus, Throwable ex) {
    if (httpStatus == HttpStatus.BAD_REQUEST) {
//            return FactoryResponse.error(httpStatus.value(),
//                    ex.getMessage()
//                    , null);
      return ResponseEntity.status(httpStatus).body(ex.getMessage());
    } else {
      return ResponseEntity.status(httpStatus).body("예상치 못한 에러가 발생 했습니다. 빠른 시일 내에 해결하도록 하겠습니다.");
    }
  }

  private static ResponseEntity createErrorResponseEntity(HttpStatus httpStatus, String message) {
    return CustomResponse.error(httpStatus, message);
  }

  private ResponseEntity createErrorResponseEntity(HttpStatus httpStatus, String message, Throwable ex) {
    writeExceptionToErrorLog(ex);
    return CustomResponse.error(httpStatus, message);
  }
}
