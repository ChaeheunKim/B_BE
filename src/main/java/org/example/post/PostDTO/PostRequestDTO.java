package org.example.post.PostDTO;

import lombok.Getter;
import lombok.Setter;
import org.example.post.PostEntity.Category;
import org.example.user.UserEntity.Part;
import org.example.post.PostEntity.Post;
import org.example.post.PostEntity.ProjectCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostRequestDTO {

    private int post_id;
    private String title;
    private String content;
    private Category category;
    private List<Map<String,Object>>  participant;
    private ProjectCategory projectCategory;
    private LocalDateTime period; // 날짜 데이터 수정 필요
    private boolean imgThumbnail; // 썸네일 번호 논의 필요
    private Part part;

    public Post toEntity(PostRequestDTO dto){
        return Post.builder()
                .title(dto.title)
                .content(dto.content)
                .category(dto.category)
                .participant(toString(participant))
                .projectCategory(dto.projectCategory)
                .period(dto.period)
                .part(dto.part)
                .build();
    }

    public String toString(List<Map<String,Object>> participant){
        if (participant == null || participant.isEmpty()) {
            return "";
        }
        return participant.stream()
                .map(map -> (String) map.get("name"))
                .filter(name -> name != null && !name.isEmpty())
                .collect(Collectors.joining(","));
    }
}
