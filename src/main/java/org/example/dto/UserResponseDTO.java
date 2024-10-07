package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserResponseDTO {

    public static class SignupResponseDTO {
        String email;
        String password;
        String name;
        int cardinal;
        String part;
        boolean state;

        public SignupResponseDTO(String email, String password, String name, int cardinal, String part,boolean state) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.cardinal=cardinal;
            this.part=part;
            this.state=state;
        }

    }
    @Getter
    @Setter
    public static class LoginResponseWithTokenDTO {
        private LoginResponseDTO loginResponseDTO;
        private String token;

        public LoginResponseWithTokenDTO(LoginResponseDTO loginResponseDTO, String token) {
            this.loginResponseDTO = loginResponseDTO;
            this.token = token;
        }
    }

    @Getter
    @Setter
    public static class LoginResponseDTO {

        String email;
        String password;


        public LoginResponseDTO(String email, String password) {
            this.email = email;
            this.password=password;
        }

    }

    public static class VerficationResponseDTO{
        String email;

        public VerficationResponseDTO(String email){
            this.email=email;
        }

    }

}
