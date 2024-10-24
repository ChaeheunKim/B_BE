package org.example.user.UserRepository;


import org.example.user.UserEntity.User;
import org.example.user.UserEntity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    UserImage findByUser(User user);
}
