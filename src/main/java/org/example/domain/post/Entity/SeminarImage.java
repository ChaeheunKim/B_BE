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
@Table(name = "SeminarImage")
public class SeminarImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seminar_id")
    private Seminar seminar;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "thumbnail", nullable = false)
    private boolean thumbnail;

    public SeminarImage (String name,String url,boolean thumbnail){
        this.name=name;
        this.url=url;
        this.thumbnail=thumbnail;
    }


}