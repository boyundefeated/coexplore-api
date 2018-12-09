package com.coexplore.api.common.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coexplore.api.common.response.ResponseStatus;
import com.coexplore.api.common.response.StandardResponse;

/**
 * A convenient implementation class for
 * {@link ControllerAdvice @ControllerAdvice} classes that wish to provide
 * centralized exception handling across all {@code @RequestMapping} methods
 * through {@code @ExceptionHandler} methods.
 *
 * @author duy.nh
 */
@ControllerAdvice
public class DefaultExceptionAdvice extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(DefaultExceptionAdvice.class);

	@ExceptionHandler({ CoExploreException.class })
	public <T> ResponseEntity<Object> handleInternalException(CoExploreException ex) {
		log.error(ex.toString());
		ex.printStackTrace();
		StandardResponse<String> response = new StandardResponse<String>();
		response.setResultCode(ex.getResultCode());
		response.setMessage(ex.getMessage());
		response.setValue(ex.getValue());
		return ResponseEntity.status(getHttpStatus(ex.getResultCode())).body(response);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public <T> ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex) {
		log.error(ex.toString());
		return handleInternalException(new CoExploreException(ResponseStatus.AUTHENTICATE_FAILED, ex.getMessage()));
	}

	@ExceptionHandler({ BadCredentialsException.class })
	public <T> ResponseEntity<Object> handleBadCredentialsException(final BadCredentialsException ex) {
		log.error(ex.toString());
		return handleInternalException(new CoExploreException(ResponseStatus.AUTHENTICATE_FAILED, ex.getMessage()));
	}

	@ExceptionHandler(value = { InsufficientAuthenticationException.class })
	public <T> ResponseEntity<Object> insufficientAuthenticationException(Exception ex, HttpServletRequest req)
			throws IOException {
		log.error(ex.toString());
		return handleInternalException(new CoExploreException(ResponseStatus.AUTHENTICATE_FAILED, ex.getMessage()));
	}

	@ExceptionHandler(value = { RuntimeException.class, Exception.class })
	public <T> ResponseEntity<Object> internalServerException(Exception ex, HttpServletRequest req) throws IOException {
		log.error("INTERNAL SERVER ERROR EXCEPTION: ", ex);
		return handleInternalException(new CoExploreException(ResponseStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.warn("Missing Request Param");
		return handleInternalException(new CoExploreException(ResponseStatus.MISSING_PARAMS));
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.warn("Invalid Type Param");
		return handleInternalException(new CoExploreException(ResponseStatus.INVALID_TYPE_PARAM));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.warn("Invalid Body");
		return handleInternalException(new CoExploreException(ResponseStatus.INVALID_BODY));
	}

	public int getHttpStatus(String code) {
		return Integer.parseInt(code.substring(0, 3));
	}

}
