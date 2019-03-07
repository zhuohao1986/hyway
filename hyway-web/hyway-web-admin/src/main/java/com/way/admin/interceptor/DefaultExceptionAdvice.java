package com.way.admin.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.way.common.constant.CodeConstants;
import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientException;
import com.way.common.exception.DefaultError;
import com.way.common.exception.IError;
import com.way.common.exception.ServerException;
import com.way.common.exception.SystemException;
import com.way.common.stdo.Result;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Desc <p>Controller统一异常advice</p>
 *
 * @author 
 * @date 2017/7/3
 */
@ControllerAdvice
@ResponseBody
public class DefaultExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Result> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.error("参数解析失败", e);
        Result response = new Result();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Result> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LOGGER.error("不支持当前请求方法", e);
        Result response =new Result(CodeConstants.RESULT_FAIL,DefaultError.METHOD_NOT_SUPPORTED);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Result> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        LOGGER.error("不支持当前媒体类型", e);
        Result response = new Result(CodeConstants.RESULT_FAIL,DefaultError.CONTENT_TYPE_NOT_SUPPORT);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Result> handleSQLException(SQLException e) {
        LOGGER.error("服务运行SQLException异常", e);
        Result response = new Result(CodeConstants.RESULT_FAIL,DefaultError.SQL_EXCEPTION);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 所有异常统一处理
     *
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleException(Exception ex) {
        LOGGER.error("未知异常", ex);
        IError error;
        String extMessage = null;
        if (ex instanceof BusinessException) {
            error = ((BusinessException) ex).getError();
            extMessage = ((BusinessException) ex).getExtMessage();
        } else if (ex instanceof BindException) {
            error = DefaultError.INVALID_PARAMETER;
            List<ObjectError> errors = ((BindException) ex).getAllErrors();
            if (errors != null && errors.size() != 0) {
                StringBuilder msg = new StringBuilder();
                for (ObjectError objectError : errors) {
                    msg.append("Field error in object '" + objectError.getObjectName() + " ");
                    if (objectError instanceof FieldError) {
                        msg.append("on field " + ((FieldError) objectError).getField() + " ");
                    }
                    msg.append(objectError.getDefaultMessage() + " ");
                }
                extMessage = msg.toString();
            }
        } else if (ex instanceof MissingServletRequestParameterException) {
            error = DefaultError.INVALID_PARAMETER;
            extMessage = ex.getMessage();
        } else if (ex instanceof ConstraintViolationException) {
            error = DefaultError.INVALID_PARAMETER;
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            final StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<?> constraintViolation : violations) {
                msg.append(constraintViolation.getPropertyPath()).append(":").append(constraintViolation.getMessage() + "\n");
            }
            extMessage = msg.toString();
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            error = DefaultError.CONTENT_TYPE_NOT_SUPPORT;
            extMessage = ex.getMessage();
        } else if (ex instanceof HttpMessageNotReadableException) {
            error = DefaultError.INVALID_PARAMETER;
            extMessage = ex.getMessage();
        } else if (ex instanceof MethodArgumentNotValidException) {
            error = DefaultError.INVALID_PARAMETER;
            extMessage = ex.getMessage();
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            error = DefaultError.METHOD_NOT_SUPPORTED;
            extMessage = ex.getMessage();
        } else if (ex instanceof UnexpectedTypeException) {
            error = DefaultError.INVALID_PARAMETER;
            extMessage = ex.getMessage();
        } else if (ex instanceof NoHandlerFoundException) {
            error = DefaultError.SERVICE_NOT_FOUND;
            extMessage = ex.getMessage();
        } else {
            error = DefaultError.SYSTEM_INTERNAL_ERROR;
            extMessage = ex.getMessage();
        }
        Result response = new Result(CodeConstants.RESULT_FAIL,error);
        response.setMessage(extMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * BusinessException 业务异常处理
     *
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result> handleException(BusinessException e) {
        LOGGER.error("业务异常", e);
        Result response = new Result(CodeConstants.RESULT_FAIL,e.getError());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * ClientException 客户端异常 给调用者 app,移动端调用
     *
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Result> handleException(ClientException e) {
    	Result response = new Result(CodeConstants.RESULT_FAIL,DefaultError.CLIENT_EXCEPTION);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * ServerException 服务端异常, 微服务服务端产生的异常
     *
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Result> handleException(ServerException e) {
    	Result response = new Result(CodeConstants.RESULT_FAIL,DefaultError.SERVER_EXCEPTION);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * SystemException 系统异常 一般指系统基础服务产生的异常
     *
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Result> handleException(SystemException e) {
    	Result response = new Result(CodeConstants.RESULT_FAIL,DefaultError.SYSTEM_INTERNAL_ERROR);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
