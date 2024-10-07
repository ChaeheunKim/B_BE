package org.example.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Role;
import org.example.entity.User;
import org.springframework.stereotype.Component;

public class UserRequestDTO {
    //회원가입
    @Getter
    @Setter
    @Component
    public static class signupRequestDTO{
        @NotNull(message = "이름은 필수 입력 값입니다.")
        private  String name;

        @NotNull(message = "이메일은 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "올바른 이메일 형식이 아닙니다.")
        private  String email;

        @NotNull(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        @NotNull(message = "기수는 필수 입력 값입니다.")
        private int cardinal;

        @NotNull(message = "파트는 필수 입력 값입니다.")
        private String part;
        private boolean state;


        public User toEntity(Role role) {
            return User.builder()
                    .name(name)
                    .password(password)
                    .cardinal(cardinal)
                    .part(part)
                    .grade(role)
                    .email(email)
                    .state(state)
                    .build();
        }


    }

    //로그인
    @NoArgsConstructor
    @Getter
    @Setter
    public static class LoginRequestDTO {
        @NotNull(message = "이메일은 필수 입력 값입니다.")
        private  String email;

        @NotNull(message = "비밀번호는 필수 입력 값입니다.")
        private String password;
        private boolean state;
        public LoginRequestDTO(String email, String password,boolean state) {
            this.email = email;
            this.password = password;
            this.state=state;
        }

    }

   //이메일 인증 - 이메일 전송
    @NoArgsConstructor
    @Getter
    public static class SendEmailRequestDTO {
        @NotNull(message = "이메일은 필수 입력 값입니다.")
        private String email;

        public SendEmailRequestDTO(String email) {
            this.email = email;
        }
    }

   //이메일 인증 - 이메일 인증코드
    @NoArgsConstructor
    @Getter
    @Setter
    public static class VerficationRequestDTO {
        @NotNull(message = "이메일은 필수 입력 값입니다.")
        private String email;

        @NotNull(message = "인증코드는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{1,6}$", message = "인증코드가 올바르지 않습니다..")
        private String usercode;

        public VerficationRequestDTO(String email, String usercode) {
            this.email = email;
            this.usercode = usercode;
        }
    }


}
