package org.example.domain.post.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.Service.CommonService;
import org.example.domain.user.UserEntity.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "project")
public class Project extends BaseEntity {
    CommonService commonService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) // TEXT 사용 필요
    private String content;

    @OneToMany(mappedBy = "project", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ProjectImage> images = new ArrayList<>();

    @Column(name = "participant", nullable = false) // Enum 사용필요
    private String participant;


    @Column(name = "period", nullable = false) // 날짜를 @Valid해서 유효성 체크해줘야함.
    private LocalDateTime period;

    @Enumerated(EnumType.STRING)
    @Column(name = "projectCategory", nullable = true)
    private ProjectCategory  projectCategory;

    public Project(PostRequestDTO postRequestDTO){
        this.title=postRequestDTO.getTitle();
        this.content=postRequestDTO.getContent();
        this.period=postRequestDTO.getPeriod();
        this.participant= commonService.toString(postRequestDTO.getParticipant());
        this.projectCategory=postRequestDTO.getProjectCategory();
    }

    public void update(PostRequestDTO postRequestDTO,List<ProjectImage> images) {
        this.title = postRequestDTO.getTitle();
        this.content = postRequestDTO.getContent();
        this.participant= commonService.toString(postRequestDTO.getParticipant());
        this.period=postRequestDTO.getPeriod();
        this.images=images;
        this.projectCategory=postRequestDTO.getProjectCategory();
    }






}