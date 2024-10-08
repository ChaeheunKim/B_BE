package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Category;
import org.example.entity.Post;
import org.example.entity.ProjectCategory;

import java.util.List;

@Getter
@Setter
public class PostRequestDTO {

    private int post_id;
    private String title;
    private String content;
    private Category category;
    private String  participant;
    private ProjectCategory projectCategory;
    private String period; // 날짜 데이터 수정 필요
//    private int img_thumbnail; 썸네일 번호 논의 필요
    private List<ImageRequestDTO > images;

    public Post toEntity(PostRequestDTO dto){
        return Post.builder()
                .title(dto.title)
                .content(dto.content)
                .category(dto.category)
                .participant(dto.participant)
                .projectCategory(dto.projectCategory)
                .period(dto.period)
                .build();
    }
}
