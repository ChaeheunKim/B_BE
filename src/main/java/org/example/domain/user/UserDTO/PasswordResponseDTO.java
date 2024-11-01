package org.example.domain.user.UserDTO;

public class PasswordResponseDTO {
    private String status;  // 성공 또는 실패 상태
    private Object response;  // 응답 데이터
    private String error;  // 에러 메시지 (에러가 없을 경우 null)

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
