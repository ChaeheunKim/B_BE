package org.example.user.UserDTO;

import lombok.Getter;
import lombok.Setter;
import org.example.user.UserEntity.BaseEntity;
import org.example.user.UserEntity.Part;
import org.example.user.UserEntity.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class UserResponseDTO  {


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
        Role role;




        public LoginResponseDTO(String email, String password, Role role) {
            this.email = email;
            this.password=password;
            this.role = role;
        }

    }

    @Getter
    @Setter
    public static class adminWaitingResponseDTO extends BaseEntity {
        String name;

        String email;
        LocalDateTime  created_at;



        public adminWaitingResponseDTO(String email, String name, LocalDateTime created_at) {
            this.email = email;
            this.name = name;
            this.created_at=created_at;
        }

    }

    @Getter
    @Setter
    public static class adminmemberResponseDTO extends BaseEntity {
        String name;

        String email;
        int cardinal;
        Part part;



        public adminmemberResponseDTO(String email, String name,int cardinal,Part part) {
            this.email = email;
            this.name = name;
            this.cardinal=cardinal;
            this.part=part;
        }

    }

    @Getter
    @Setter
    public static class VerficationResponseDTO{
        String email;

        public VerficationResponseDTO(String email){
            this.email=email;
        }

    }

}
