package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestDTO {

    private int postId;
    private String user_id;
    private String title;
    private String intro;
    private String content;
    private String category;
    private String url;
    private String  participant;
    private char isProject;
    private String period;
    private int img_thumbnail;
    private List<ImageRequestDTO> images;
}
