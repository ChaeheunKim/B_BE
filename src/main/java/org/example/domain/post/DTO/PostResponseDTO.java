package org.example.domain.post.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.post.Entity.Project;
import org.example.domain.post.Entity.ProjectCategory;
import org.example.domain.user.UserEntity.Part;

import java.time.LocalDateTime;
import java.util.List;

//public class PostResponseDTO {
//    @Getter
//    @Setter
//    public static class PostDetailDTO{
//        private int post_id;
//        private String title;
//        private String content;
//        private String image;
//        private Category category;
//        private String participant;
//        private ProjectCategory projectCategory;
//        private LocalDateTime period;
//
//        public PostDetailDTO(Post post, PostImage postImage){
//            this.post_id = post.getPostId();
//            this.title = post.getTitle();
//            this.content = post.getContent();
//            this.image = postImage.getImgUrl();
//            this.category = post.getCategory();
//            this.participant = post.getParticipant();
//            this.projectCategory = post.getProjectCategory();
//            this.period = post.getPeriod();
//
//        }
//    }
//
//    @Getter
//    @Setter
//    public static class PostDTO{
//        private int post_id;
//        private String title;
//        private String image;
//        private Category category;
//        private ProjectCategory ProjectCategory;
//        private LocalDateTime period;
//
//        public PostDTO(Post post, PostImage image){
//            this.post_id = post.getPostId();
//            this.title = post.getTitle();
//            this.image = image.getImgUrl();
//            this.category = post.getCategory();
//            this.ProjectCategory = post.getProjectCategory();
//            this.period = post.getPeriod();
//        }
//    }
//}
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
        private Part part;


        public  ProjectItem(String post_title, String image, ProjectCategory project_category) {
            this.post_title = post_title;
            this.image = image;
            this.project_category = project_category;
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
    public static class AdminPostResponseDTO {
        private String post_title;
        private int post_id;
        private LocalDateTime period;



        public AdminPostResponseDTO( int post_id,String post_title,LocalDateTime period) {
            this.post_id=post_id;
            this.post_title=post_title;
            this.period= period;

        }
}



}
