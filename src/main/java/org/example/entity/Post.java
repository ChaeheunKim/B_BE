package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.dto.ActivityResponseDTO;
import org.example.dto.PostRequestDTO;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_id;

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @OneToOne
    @JoinColumn(name = "acti_id")
    private Activity activity;

    @Column(nullable = false)
    private String post_title;

    @Column
    private String post_intro;

    @Column(nullable = false)
    private String post_content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Post(String user_id, List<Image> images , Activity activity, String post_title, String post_content, String post_intro){
        this.user_id = user_id;
        this.images = images;
        this.activity = activity;
        this.post_title = post_title;
        this.post_content = post_content;
        this.post_intro = post_intro;
        this.createdAt = LocalDateTime.now();
//        this.created_at = created_at;
    }

}
