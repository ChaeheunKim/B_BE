package org.example.domain.user.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRequestDTO {

    private String userId;
    private String imgName;
    private String imgUrl;
    private String imgOriginName;
    private int imgSize;
    private String imgExt;
    private char imgThumbnail;
}