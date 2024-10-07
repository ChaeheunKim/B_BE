package org.example.dto;

public class ImageResponseDTO {
    private String status;  // 요청 성공 여부 (success or error)
    private Object response;  // 성공 시의 응답 데이터
    private String error;  // 오류 발생 시의 에러 메시지

    // getter와 setter 메서드
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
