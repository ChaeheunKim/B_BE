package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) // TEXT 사용 필요
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false) // Enum 사용필요
    private Category category;

    @Column(name = "participant", nullable = false) // Enum 사용필요
    private String  participant;

    @Enumerated(EnumType.STRING)
    @Column(name = "projectCategory", nullable = false)
    private ProjectCategory  projectCategory;

    @Column(name = "period", nullable = false) // 날짜를 @Valid해서 유효성 체크해줘야함.
    private LocalDateTime period;

    /*
    @Builder
    public Post(int postId, List<PostImage> images , String title, String content,
                 Category category, ProjectCategory projectCategory, LocalDateTime period){
{
        this.postId = postId;
        this.images = images;
        this.title = title;
        this.content = content;
        this.category = category;
        this.projectCategory = projectCategory;
        this.period = period;
    }
*/
}
