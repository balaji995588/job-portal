package com.jobportal.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		log.error(ex.getMessage());
		return responseEntity;
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> accessDeniedExceptionHandler(AccessDeniedException ex) {

		ErrorResponseDto error = new ErrorResponseDto("Access Denied", ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
		return new ResponseEntity<ErrorResponseDto>(
				new ErrorResponseDto(ErrorKeyConstants.DATA_E031801, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		List<String> error = new ArrayList<String>();
		for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
			error.add(objectError.getDefaultMessage());
		}

		ErrorResponseDto response = new ErrorResponseDto();
		System.out.println(error);
		response.setKey(ErrorKeyConstants.DATA_E031801);
		response.setMessage(error.get(0));

		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(response,
				HttpStatus.BAD_REQUEST);
		log.error(response.toString());
		// log.info(error.get(0));
		return responseEntity;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
		ErrorResponseDto response = new ErrorResponseDto();
		response.setKey(ErrorKeyConstants.DATA_E031802);
		response.setMessage(ex.getMessage());
		log.error(ex.getMessage());
		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(response,
				HttpStatus.BAD_REQUEST);
		return responseEntity;
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> expiredJwtException(ExpiredJwtException ex) {

		ErrorResponseDto response = new ErrorResponseDto();
		response.setKey(ErrorKeyConstants.TOKEN_E031902);
		response.setMessage(ErrorMessageConstants.TOKEN_EXPIRED);
		log.error(ex.getMessage());
		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(response,
				HttpStatus.UNAUTHORIZED);
		return responseEntity;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<?> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
		ErrorResponseDto response = new ErrorResponseDto();
		response.setKey(ErrorKeyConstants.URL_E032001);
		response.setMessage(ErrorMessageConstants.URL_NOT_FOUND);

		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(response,
				HttpStatus.BAD_REQUEST);

		return responseEntity;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setKey("Method not allowed");
		error.setMessage("Please check the method type");
		log.error(ex.getMessage());
		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(error,
				HttpStatus.BAD_REQUEST);

		return responseEntity;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> ExceptionHandler(Exception ex) {

		// Handle the exception
		ErrorResponseDto error = new ErrorResponseDto();
		error.setKey("Something went wrong");
		error.setMessage("Internal server error occurred");
		log.error(ex.getMessage());
		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(error,
				HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

	@ExceptionHandler(InvalidEmailOrPasswordException.class)
	public ResponseEntity<?> invalidEmailOrPasswordExceptionHandler(InvalidEmailOrPasswordException ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setKey(ex.getKey());
		error.setMessage(ex.getMessage());
		log.error(ex.getMessage());
		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(error,
				HttpStatus.BAD_REQUEST);

		return responseEntity;
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> ioExceptionhandler(IOException ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setKey("Invalid file");
		error.setMessage(ex.getMessage());
//		log.error(ex.getMessage());

		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(error,
				HttpStatus.BAD_REQUEST);

		return responseEntity;
	}

	@ExceptionHandler(ClientAbortException.class)
	public ResponseEntity<?> clientAbortExceptionhandler(ClientAbortException ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setKey("Connection reset");
		error.setMessage(ex.getMessage());
//		log.error(ex.getMessage());

		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(error,
				HttpStatus.BAD_REQUEST);

		return responseEntity;
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> nullPointerExceptionHandler(NullPointerException ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setKey(ErrorKeyConstants.DATA_E031802);
		error.setMessage(ex.getMessage());

		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(error,
				HttpStatus.BAD_REQUEST);

		return responseEntity;
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<?> numberFormatException(NumberFormatException ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setKey(ErrorKeyConstants.DATA_E031802);
		error.setMessage(ex.getMessage());
		ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(error,
				HttpStatus.BAD_REQUEST);

		return responseEntity;
	}
}
