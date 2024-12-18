package org.example.domain.user.UserDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.example.domain.user.UserEntity.BaseEntity;
import org.example.domain.user.UserEntity.Part;
import org.example.domain.user.UserEntity.Role;
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
        private String accesstoken;


        public LoginResponseWithTokenDTO(LoginResponseDTO loginResponseDTO, String accesstoken) {
            this.loginResponseDTO = loginResponseDTO;
            this.accesstoken = accesstoken;

        }
    }

    @Getter
    @Setter
    public static class LoginResponseDTO {

        String email;
        String password;
        Role role;
        String image;




        public LoginResponseDTO(String email, String password, Role role,String image) {
            this.email = email;
            this.password=password;
            this.role = role;
            this.image=image;
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
