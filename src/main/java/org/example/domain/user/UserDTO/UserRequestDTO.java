package org.example.domain.user.UserDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.user.UserEntity.Part;
import org.example.domain.user.UserEntity.Role;
import org.example.domain.user.UserEntity.State;
import org.example.domain.user.UserEntity.User;
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
        private int user_cardinal;

        @NotNull(message = "파트는 필수 입력 값입니다.")
        private Part user_part;

        private State state;

        public User toEntity(Role role, State state) {
            return User.builder()
                    .name(name)
                    .password(password)
                    .cardinal(user_cardinal)
                    .part(user_part)
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
        private State state;
        public LoginRequestDTO(String email, String password, State state) {
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
