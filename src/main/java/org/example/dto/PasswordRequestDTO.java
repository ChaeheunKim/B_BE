package org.example.dto;

public class PasswordRequestDTO {
    private String password;     // 현재 비밀번호
    private String newpassword;  // 새 비밀번호 (8자 이상)

    // getter와 setter 메서드
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
