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
    private List<String>  participant;
    private ProjectCategory projectCategory;
    private String period; // 날짜 데이터 수정 필요
    private boolean imgThumbnail; // 썸네일 번호 논의 필요
    private List<ImageRequestDTO> images;

    public Post toEntity(PostRequestDTO dto){
        return Post.builder()
                .title(dto.title)
                .content(dto.content)
                .category(dto.category)
                .participant(toString(participant))
                .projectCategory(dto.projectCategory)
                .period(dto.period)
                .build();
    }

    public String toString(List<String> participant){
        if (participant == null || participant.isEmpty()) {
            return "";
        }
        return String.join(",", participant);
    }
}
