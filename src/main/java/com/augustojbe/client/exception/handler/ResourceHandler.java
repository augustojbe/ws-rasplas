package com.augustojbe.client.exception.handler;

import com.augustojbe.client.dto.error.ErrosResponseDto;
import com.augustojbe.client.exception.BadRequestException;
import com.augustojbe.client.exception.BussinesException;
import com.augustojbe.client.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrosResponseDto> notFoundException(NotFoundException ex ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrosResponseDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrosResponseDto> badRequestException(BadRequestException ex ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrosResponseDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrosResponseDto> badRequestException(MethodArgumentNotValidException ex ) {
        Map<String, String> messages = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors().forEach(error ->{
                    String field = ((FieldError) error).getField();
                    String defaulMessage = error.getDefaultMessage();
                    messages.put(field, defaulMessage);

                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrosResponseDto.builder()
                .message(Arrays.toString(messages.entrySet().toArray()))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrosResponseDto> badRequestException(DataIntegrityViolationException ex ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrosResponseDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<ErrosResponseDto> badRequestException(BussinesException ex ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrosResponseDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.CONFLICT)
                .statusCode(HttpStatus.CONFLICT.value())
                .build());
    }



}
