package com.esmt.response.dto;

 
import org.springframework.http.HttpStatus;

import com.esmt.dto.ErrorDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
 private boolean success;   
 private String message;    
 private T data;            
 private ErrorDetails errorDetails;
 private HttpStatus status;        
 private String path;             
 private long timestamp;  
 
 public static <T> ApiResponse<T> success(T data) {
     return ApiResponse.<T>builder()
             .success(true)
             .message("Request successful")
             .data(data)
             .build();
 }

 public static <T> ApiResponse<T> success(String message, T data) {
     return ApiResponse.<T>builder()
             .success(true)
             .message(message)
             .data(data)
             .build();
 }
 }
