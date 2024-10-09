package org.example.entity;

import org.example.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityProvider<T> {
    // 성공 응답:  데이터 포함
    public <T> ResponseEntity<ApiResponseDTO<T>> successWithData(String message, T data) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>(true, message, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 성공 응답: 데이터 미포함
    public <T> ResponseEntity<ApiResponseDTO<T>> successWithoutData(String message) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>(true, message,null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 실패 응답:  데이터 포함
    public <T> ResponseEntity<ApiResponseDTO<T>> FailWithData(String message, T data) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>(false, message, data);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 실패 응답: 데이터 미포함
    public <T> ResponseEntity<ApiResponseDTO<T>> FailWithoutData(String message) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>(false, message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}