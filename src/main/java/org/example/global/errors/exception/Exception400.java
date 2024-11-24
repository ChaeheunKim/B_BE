package org.example.global.errors.exception;


import lombok.Getter;
import org.example.global.errors.ErrorCode;
import org.example.global.response.ApiResponseDTO;
import org.example.global.response.ResponseEntityProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;



@Getter
public class Exception400 extends RuntimeException {

    Map<String, String> errors;


    public Exception400(Map<String, String> errors, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errors = (errors != null) ? errors : null;
    }

    public ResponseEntity<ApiResponseDTO<?>> body(){
        return ResponseEntityProvider.error(errors,getMessage());
    }
}
