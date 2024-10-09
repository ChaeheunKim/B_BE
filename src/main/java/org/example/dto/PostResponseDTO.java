package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Category;
import org.example.entity.Post;
import org.example.entity.ProjectCategory;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {
    @Getter
    @Setter
    public static class PostDetailDTO{
        private int post_id;
        private String title;
        private String content;
        private List<MultipartFile> images;
        private Category category;
        private String participant;
        private ProjectCategory projectCategory;
        private LocalDateTime period;

        public PostDetailDTO(Post post, List<MultipartFile> images){
            this.post_id = post.getPostId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.images = images;
            this.category = post.getCategory();
            this.participant = post.getParticipant();
            this.projectCategory = post.getProjectCategory();
            this.period = post.getPeriod();

        }
    }

    @Getter
    @Setter
    public static class PostDTO{
        private int post_id;
        private String title;
        private List<MultipartFile> images;
        private Category category;
        private ProjectCategory ProjectCategory;
        private LocalDateTime period;

        public PostDTO(Post post, List<MultipartFile> images){
            this.post_id = post.getPostId();
            this.title = post.getTitle();
            this.images = images;
            this.category = post.getCategory();
            this.ProjectCategory = post.getProjectCategory();
            this.period = post.getPeriod();
        }
    }
}
