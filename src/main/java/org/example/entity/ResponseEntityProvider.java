package org.example.entity;

import org.springframework.http.HttpStatus;

public class ResponseEntity<T> {

    public FailResponseEntitywithmessage<?> getError() {
        String errorMessage;
        ResponseEntity<?> responseEntity = new ResponseEntity<?>(errorMessage, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
