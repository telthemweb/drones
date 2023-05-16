package com.drones.dronesinnotauzen.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequest;

import com.drones.dronesinnotauzen.dto.MessageResponseDto;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
/*
|--------------------------------------------------------------------------
|                        WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/
@RestControllerAdvice
public class ExceptionHandlerController {
	 @Bean
	    public ErrorAttributes errorAttributes() {
	        return (ErrorAttributes) new DefaultErrorAttributes(){
	            @Override
	            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
	                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
	                errorAttributes.remove("exception");
	                return errorAttributes;
	            }
	        };
	    }
	 
	 @ExceptionHandler(CustomException.class)
	    public ResponseEntity<MessageResponseDto> handleCustomException(CustomException ex) throws IOException {

	        MessageResponseDto responseDto =  new MessageResponseDto();
	        responseDto.setMessage(ex.getMessage());
	        responseDto.setCode(ex.getHttpStatus().value());
	        return new ResponseEntity<MessageResponseDto>(responseDto, ex.getHttpStatus());
	    }
	 
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<MessageResponseDto> handleException(HttpServletResponse res) throws IOException {
	        MessageResponseDto responseDto =  new MessageResponseDto();
	        responseDto.setMessage("An Error Occurred");
	        responseDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        return new ResponseEntity<MessageResponseDto>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
