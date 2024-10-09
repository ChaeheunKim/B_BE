package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Part;

public class MemberResponseDTO {

    @Getter
    @Setter
    private String name;
    private Part part;
    private int cardinal;

    public MemberResponseDTO(String name, Part part,int cardinal) {
        this.name = name;
        this.cardinal=cardinal;
        this.part=part;
    }
}
