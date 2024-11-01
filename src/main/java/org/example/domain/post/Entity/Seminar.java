package org.example.domain.post.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.post.DTO.PostRequestDTO;
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

    @Column(nullable = false) // TEXT 사용 필요
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Object> images = new ArrayList<>();

    @Column(name = "participant", nullable = false) // Enum 사용필요
    private String participant;


    @Column(name = "period", nullable = false) // 날짜를 @Valid해서 유효성 체크해줘야함.
    private LocalDateTime period;

    public Seminar toEntity(PostRequestDTO postRequestDTO,List<Object> images){
        return Seminar.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .participant(postRequestDTO.getParticipant().toString())
                .period(postRequestDTO.getPeriod())
                .images(images)
                .build();
    }

    public void update(PostRequestDTO postRequestDTO,List<Object> images) {
        this.title = postRequestDTO.getTitle();
        this.content = postRequestDTO.getContent();
        this.participant= postRequestDTO.getParticipant().toString();
        this.period=postRequestDTO.getPeriod();
        this.images=images;
    }





}