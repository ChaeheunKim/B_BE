package org.example.domain.post.Entity;

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
@Table(name = "StudyImage")
public class StudyImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "thumbnail", nullable = false)
    private boolean thumbnail;

    public StudyImage toEntity(Study study,String name,String url,boolean thumbnail){
        return StudyImage.builder()
                .name(name)
                .url(url)
                .thumbnail(thumbnail)
                .study(study).build();
    }


}