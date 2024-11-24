package org.example.domain.post.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.Service.CommonService;
import org.example.domain.user.UserEntity.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Seminar")
public class Seminar extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @OneToMany(mappedBy = "seminar", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<SeminarImage> images = new ArrayList<>();

    @Column(name = "participant", nullable = false)
    private List<String> participant;


    @Column(name = "period", nullable = false)
    private LocalDateTime period;

    public Seminar (PostRequestDTO postRequestDTO){
        this.title=postRequestDTO.getTitle();
        this.content=postRequestDTO.getContent();
        this.period=postRequestDTO.getPeriod();
        this.participant= postRequestDTO.getParticipant();
    }

    public void update(PostRequestDTO postRequestDTO,List<SeminarImage> images) {
        this.title = postRequestDTO.getTitle();
        this.content = postRequestDTO.getContent();
        this.participant= postRequestDTO.getParticipant();
        this.period=postRequestDTO.getPeriod();
        this.images=images;
    }





}