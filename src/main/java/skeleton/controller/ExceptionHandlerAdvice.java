package skeleton.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(annotations = Controller.class)
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	@Autowired
	MessageSource messageSource;

	private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	public ExceptionHandlerAdvice() {
		super();
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
		String error;
		for (FieldError fieldError : fieldErrors) {
			error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
			errors.add(error);
		}
		for (ObjectError objectError : globalErrors) {
			error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
			errors.add(error);
		}

		return new ResponseEntity<Object>(errors, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String unsupported = "Unsupported content type: " + ex.getContentType();
		String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
		return new ResponseEntity<Object>(unsupported + "\n" + supported, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable mostSpecificCause = ex.getMostSpecificCause();

		return new ResponseEntity<Object>(mostSpecificCause.getClass().getName() + "\n" + mostSpecificCause.getCause(),
				headers, status);
	}

	// 400
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
		logger.error("Database error: {} \n {}", ExceptionUtils.getRootCauseMessage(ex), ex.getStackTrace());
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ ValidationException.class, ParseException.class })
	// @ResponseStatus(value=HttpStatus.ACCEPTED)
	public ResponseEntity<Object> handleValidationError(final Exception ex, final WebRequest request) {

		logger.error("Input validation error: {} \n {}", ExceptionUtils.getRootCauseMessage(ex), ex.getStackTrace());
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	// 500
	@ExceptionHandler({ NullPointerException.class, IllegalStateException.class, IOException.class,
			RuntimeException.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request,
			HttpServletResponse response) {
		logger.error("Unexpected error: Status Code 500: ");
		logger.error(ExceptionUtils.getFullStackTrace(ex));
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { AccessDeniedException.class, IllegalArgumentException.class,
			SpelEvaluationException.class })
	public ResponseEntity<Object> defaultExceptionHandler(final Exception ex, HttpServletRequest req,
			final WebRequest request) throws URISyntaxException, MalformedURLException {

		logger.error("Expected error occured: {} \n {}", ExceptionUtils.getRootCauseMessage(ex), ex);
		logger.error(ExceptionUtils.getFullStackTrace(ex));
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.PERMANENT_REDIRECT, request);

	}

}
