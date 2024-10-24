package org.example.post.PostDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDetailResponseDTO {
    private String status;
    private DetailResponse response;
    private Object error;

    @Getter
    @Setter
    public static class Response {
        private List<PostDetailResponseDTO.DetailPostItem> post_list;
    }

    @Getter
    @Setter
    public static class DetailResponse {
        private List<DetailPostItem> detail_list;
    }
    @Getter
    @Setter
    public static class DetailPostItem{
        private String title;
        private String content;
        private LocalDateTime period;
        private List<String> image;
        private List<Participant> participant;

        @Getter
        @Setter
        public static class Participant{
            private String name;
        }
    }
}
