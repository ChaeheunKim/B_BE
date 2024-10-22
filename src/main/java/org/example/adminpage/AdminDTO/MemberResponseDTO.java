package org.example.adminpage.AdminDTO;

import lombok.Getter;
import lombok.Setter;
import org.example.user.UserEntity.Part;
@Getter
@Setter
public class MemberResponseDTO {


    private String name;
    private Part part;
    private int cardinal;
    private String imageurl;
    private Long user_id;

    public MemberResponseDTO(String name, Part part,int cardinal,String imageurl,Long user_id) {
        this.name = name;
        this.cardinal=cardinal;
        this.part=part;
        this.imageurl=imageurl;
        this.user_id=user_id;
    }
}
