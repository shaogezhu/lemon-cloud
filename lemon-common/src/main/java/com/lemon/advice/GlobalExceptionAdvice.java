package com.lemon.advice;

import com.lemon.exception.HttpException;
import com.lemon.exception.UnifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GlobalExceptionAdvice
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;
    /**
     *处理所有未知异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<UnifyResponse> handlerException(HttpServletRequest req, Exception e){
        String url = req.getRequestURI();
        String method = req.getMethod();
        log.error("请求地址: {}\n异常信息: {}",url+" "+method,e.getMessage());
        log.error("",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UnifyResponse(9999,"服务器异常",method+" "+url));
    }

    /**
     * 处理各种各样的自定义异常
     */
    @ExceptionHandler(value = {HttpException.class})
    @ResponseBody
    public ResponseEntity<UnifyResponse> handlerHttpException(HttpServletRequest req, HttpException e){
        String url = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse unifyResponse = new UnifyResponse(e.getCode(),codeConfiguration.getMessage(e.getCode()),method+" "+url);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        httpStatus = httpStatus==null?HttpStatus.INTERNAL_SERVER_ERROR:httpStatus;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(unifyResponse,headers,httpStatus);
    }

    /**
     * 处理Violation参数异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnifyResponse handlerConstraintViolationException(HttpServletRequest req, ConstraintViolationException e){
        String url = req.getRequestURI();
        String method = req.getMethod();
        String message = e.getMessage();
        return new UnifyResponse(10001, message, method + " " + url);
    }

    /**
     * 处理方法接口中Violation参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return new UnifyResponse(10001, message,method + " " + requestUrl);
    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append(';'));
        return errorMsg.toString();
    }

}
