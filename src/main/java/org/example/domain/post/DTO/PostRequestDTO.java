package org.example.domain.post.DTO;

import lombok.Getter;
import lombok.Setter;

import org.example.domain.user.UserEntity.Part;
import org.example.domain.post.Entity.ProjectCategory;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class PostRequestDTO {

    private int post_id;
    private String title;
    private String content;
    private List<String> participant;
    private ProjectCategory projectCategory;
    private LocalDateTime period;
    private int imgThumbnail_id;
    private Part part;




}
