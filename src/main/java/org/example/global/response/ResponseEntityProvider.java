package org.example.global.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.example.global.errors.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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

    // 실패 응답: 데이터 미포함, 에러 코드 포함
    public <T> ResponseEntity<ApiResponseDTO<T>> FailWithoutData(ErrorCode errorCode) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>(false, errorCode.getMessage(), null);
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    public static ResponseEntity<ApiResponseDTO<?>> error(Map<String, String> errors, String message) {
        ApiResponseDTO<?> response = new ApiResponseDTO<>(false, message, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}



