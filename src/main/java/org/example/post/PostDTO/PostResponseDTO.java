package org.example.post.PostDTO;

import lombok.Getter;
import lombok.Setter;
import org.example.post.PostEntity.Category;

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
    private Response response;
    private Object error;

    @Getter
    @Setter
    public static class Response {
        private List<PostItem> post_list;
    }
    @Getter
    @Setter
    public static class PostItem {
        private int post_id;
        private String post_title;
        private String image;
        private String project_category;
        private LocalDateTime period;
        private String part;
    }

    @Getter
    @Setter
    public static class AdminPostResponseDTO {
        private String post_title;
        private int post_id;
        private LocalDateTime period;
        private Category category;


        public AdminPostResponseDTO( int post_id,String post_title,Category category,LocalDateTime period) {
            this.category=category;
            this.post_id=post_id;
            this.post_title=post_title;
            this.period= period;

        }
}



}
