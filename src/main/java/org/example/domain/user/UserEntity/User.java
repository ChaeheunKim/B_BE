package org.example.domain.user.UserEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.Entity.ProjectImage;
import org.example.domain.user.UserDTO.MypageReqeustDTO;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cardinal", nullable = false)
    private Integer cardinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;


    @Enumerated(EnumType.STRING)
    @Column(name = "part", nullable = false)
    private Part part;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private Role grade;

    @Column(name = "email", nullable = false)
    private String email;

    @Builder
    public User(String password, String name, Integer cardinal, Part part,  Role grade,String email,State state) {
       this.password=password;
       this.name=name;
       this.cardinal=cardinal;
       this.part=part;
       this.grade=grade;
       this.email=email;
       this.state=state;
    }
    public void update(MypageReqeustDTO mypageRequestDTO) {
        this.name=mypageRequestDTO.getName();
        this.email=mypageRequestDTO.getEmail();
        this.cardinal=mypageRequestDTO.getCardinal();
        this.part=mypageRequestDTO.getPart();
    }


}
