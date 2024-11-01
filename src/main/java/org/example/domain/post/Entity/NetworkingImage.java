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
@Table(name = "NetworkingImage")
public  class NetworkingImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "networking_id")
    private Networking networking;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "thumbnail", nullable = false)
    private boolean thumbnail;
    public NetworkingImage toEntity(Networking networking,String name,String url,boolean thumbnail){
        return NetworkingImage.builder()
                .name(name)
                .url(url)
                .thumbnail(thumbnail)
                .networking(networking).build();
    }




}
