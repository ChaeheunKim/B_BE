package org.example.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "image")
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int img_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "img_name", nullable = false)
    private String img_name;

    @Column(name = "img_url", nullable = false)
    private String img_url;

    @Column(name = "img_originName", nullable = false)
    private String img_originName;

    @Column(name = "img_size", nullable = false)
    private Long img_size;

    @Column(name = "img_ext", nullable = false)
    private String  img_ext;

    @Column(name = "img_thumbnail", nullable = false)
    private char img_thumbnail;

    @Builder
    public Image(Post post,String img_name, String img_url, String img_originName, Long img_size, String img_ext, char img_thumbnail){
        this.post = post;
        this.img_name = img_name;
        this.img_url = img_url;
        this.img_originName = img_originName;
        this.img_size = img_size;
        this.img_ext = img_ext;
        this.img_thumbnail = img_thumbnail;
    }
}

