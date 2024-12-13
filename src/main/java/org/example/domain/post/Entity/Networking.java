package org.example.domain.post.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.Service.StringConverter;
import org.example.domain.user.UserEntity.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Networking")
public class Networking  extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) // TEXT 사용 필요
    private String content;


    @OneToMany(mappedBy = "networking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NetworkingImage> images = new ArrayList<>();

    @Column(name = "participant", nullable = false, columnDefinition = "TEXT")
    //db에 String으로 저장
    @Convert(converter = StringConverter.class)
    private List<String> participant;


    @Column(name = "period", nullable = false)
    private LocalDateTime period;


    public Networking(PostRequestDTO postRequestDTO)  {
        this.title=postRequestDTO.getTitle();
        this.content=postRequestDTO.getContent();
        this.period=postRequestDTO.getPeriod();
        this.participant= postRequestDTO.getParticipant();
    }

    public void update(PostRequestDTO postRequestDTO,List<NetworkingImage> images) {
        this.title = postRequestDTO.getTitle();
        this.content = postRequestDTO.getContent();
        this.participant=postRequestDTO.getParticipant();
        this.period=postRequestDTO.getPeriod();
        this.images=images;
    }



}
