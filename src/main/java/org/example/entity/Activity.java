package org.example.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "activity")
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int acti_id;

    @Column(name = "acti_category", nullable = false)
    private String acti_category;

    @Column(name = "acti_url")
    private String acti_url;

    @Column(name = "acti_participant", nullable = false)
    private String acti_participant;

    @Column(name = "acti_isProject", nullable = false)
    private char acti_isProject;

    @Column(name = "acti_period", nullable = false)
    private String acti_period;

    @OneToOne(mappedBy = "activity")
    private Post post;


    @Builder
    public Activity(String acti_category, String acti_url, String acti_participant, String acti_period, char acti_isProject){
        this.acti_category = acti_category;
        this.acti_url = acti_url;
        this.acti_period = acti_period;
        this.acti_participant = acti_participant;
        this.acti_isProject = acti_isProject;
//        this.post = post;
    }
}
