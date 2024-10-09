package org.example.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

}
