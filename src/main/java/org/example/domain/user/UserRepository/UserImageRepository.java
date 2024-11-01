package org.example.domain.user.UserRepository;


import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserEntity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    UserImage findByUser(User user);
}
