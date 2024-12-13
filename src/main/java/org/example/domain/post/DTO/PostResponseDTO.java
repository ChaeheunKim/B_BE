package org.example.domain.post.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.post.Entity.Category;
import org.example.domain.post.Entity.ProjectCategory;
import org.example.domain.user.UserEntity.BaseEntity;
import org.example.domain.user.UserEntity.Part;

import java.time.LocalDateTime;


@Getter
@Setter
public class PostResponseDTO {
    private String status;
    private Object error;

    @Getter
    @Setter
    public static class ProjectItem {
        private String post_title;
        private String image;
        private ProjectCategory project_category;
        private LocalDateTime period;


        public  ProjectItem(String post_title, String image, ProjectCategory project_category,LocalDateTime period) {
            this.post_title = post_title;
            this.image = image;
            this.project_category = project_category;
            this.period=period;
        }

    }

    @Getter
    @Setter
    public static class NetworkingandSeminarItem {
        private String post_title;
        private LocalDateTime period;
        private String image;

        public  NetworkingandSeminarItem(String post_title, String image, LocalDateTime period) {
            this.post_title = post_title;
            this.image = image;
            this.period=period;
        }

    }

    @Getter
    @Setter
    public static class StudyItem {
        private String post_title;
        private LocalDateTime period;
        private String image;
        private Part part;

        public  StudyItem(String post_title, String image, LocalDateTime period,Part part) {
            this.post_title = post_title;
            this.image = image;
            this.period=period;
            this.part=part;
        }

    }




    @Getter
    @Setter
    public static class AdminPostResponseDTO extends BaseEntity {
        private String post_title;
        private int post_id;
        private LocalDateTime period;
        private Category category;
        private LocalDateTime createdAt;



        public AdminPostResponseDTO(int post_id, String post_title, LocalDateTime period, Category category, LocalDateTime createdAt) {
            this.post_id=post_id;
            this.post_title=post_title;
            this.period= period;
            this.category=category;
            this.createdAt=createdAt;

        }
}



}
