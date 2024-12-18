package org.example.domain.user.UserDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.user.UserEntity.Part;
import org.example.domain.user.UserEntity.State;
@Getter
@Setter
public class MypageReqeustDTO {
    //마이페이지

        private String name;
        private String email;
        private int cardinal;
        private Part part;
        public MypageReqeustDTO(String name, String email, int cardinal,Part part) {
            this.name=name;
            this.email =email;
            this.cardinal=cardinal;
            this.part=part;
        }

    }

