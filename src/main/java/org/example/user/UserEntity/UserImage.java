package org.example.user.UserEntity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_image")
public class UserImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int img_id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "img_name", nullable = false)
    private String img_name;

    @Column(name = "img_url", nullable = false)
    private String img_url;



    @Builder
   public UserImage(User user, String img_name, String img_url){
       this.user = user;
       this.img_name = img_name;
       this.img_url = img_url;

    }
}

