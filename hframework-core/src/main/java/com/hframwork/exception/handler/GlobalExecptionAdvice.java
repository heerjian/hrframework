package com.hframwork.exception.handler;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.hframwork.exception.model.Fail;
import com.hframwork.result.Result;
import com.hframwork.exception.FileNotFindException;
import com.hframwork.exception.model.Error;

@ControllerAdvice
@ResponseBody
class GlobalExecptionAdvice {
	private static Logger logger = LoggerFactory.getLogger(GlobalExecptionAdvice.class);
	private Result<Object> error = Result.error();

	/**
	 * 500
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Result<?> handleRuntimeException(Exception e) {
		logger.error("异常", e);
		error.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		error.setMessage(e.getMessage());
		return error;
	}

	/**
	 * 
	 * 500
	 * 
	 * @param e
	 * @return Result<?>
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public Result<?> handleRuntimeException(RuntimeException e) {
		logger.error("异常", e);
		error.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		error.setMessage(e.getMessage());
		return error;
	}

	/**
	 * 
	 * 数据校验异常
	 * 
	 * @param e
	 * @return Result<Object>
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public Result<Object> handleBindException(BindException e) {
		logger.error(e.getMessage());
		BindingResult bindingResult = e.getBindingResult();
		List<Error> msgs = new LinkedList<Error>();
		for (ObjectError error : bindingResult.getAllErrors()) {
			if (error instanceof FieldError) {
				FieldError fe = (FieldError) error;
				msgs.add(new Error(fe.getField(), error.getDefaultMessage()));
			} else {
				msgs.add(new Error(error.getObjectName(), error.getDefaultMessage()));
			}
		}
		error.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		error.setData(msgs);
		error.setMessage("数据校验异常");
		return error;
	}

	/**
	 * 404-NOT_FOUND
	 * 
	 * @param e
	 * @return Result<?>
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public Result<?> handlerNotFoundException(NoHandlerFoundException e) {
		logger.error("请求的资源不可用", e);
		return Result.error("请求的资源不可用");
	}

}
