package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Activity;
import org.example.entity.Image;

public class PostResponseDTO {
    @Getter
    @Setter
    public static class PostDTO{
        private int post_id;
        private String user_name;
        private String post_title;
        private Image img;
        private Activity activity;

    }
}
