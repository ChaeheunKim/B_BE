package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.BaseEntity;
import org.example.entity.State;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserResponseDTO  {

    public static class SignupResponseDTO {
        String email;
        String password;
        String name;
        int cardinal;
        String part;
        State state;

        public SignupResponseDTO(String email, String password, String name, int cardinal, String part,State state) {
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

    @Getter
    @Setter
    public static class adminWaitingResponseDTO extends BaseEntity {
        String name;

        String email;



        public adminWaitingResponseDTO(String email, String name) {
            this.email = email;
            this.name = name;
        }

    }

    @Getter
    @Setter
    public static class adminmemberResponseDTO extends BaseEntity {
        String name;

        String email;
        int cardinal;
        String part;



        public adminmemberResponseDTO(String email, String name,int cardinal,String part) {
            this.email = email;
            this.name = name;
            this.cardinal=cardinal;
            this.part=part;
        }

    }

    public static class VerficationResponseDTO{
        String email;

        public VerficationResponseDTO(String email){
            this.email=email;
        }

    }

}
