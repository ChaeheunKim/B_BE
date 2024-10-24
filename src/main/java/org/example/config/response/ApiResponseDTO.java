package org.example.config.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO<T> {


    private boolean success;
    private String message;
    private T data;

    public  ApiResponseDTO(boolean success, String message, T data){
        this.success=success;
        this.data=data;
        this.message=message;

    }
}
