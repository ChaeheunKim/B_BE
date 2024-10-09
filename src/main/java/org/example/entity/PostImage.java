package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_image")
public class PostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int img_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "img_name", nullable = false)
    private String imgName;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "img_thumbnail", nullable = false)
    private boolean imgThumbnail;

    @Builder
    public PostImage(Post post, String imgName, String imgUrl, boolean imgThumbnail){
        this.post = post;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.imgThumbnail = imgThumbnail;
    }
}

