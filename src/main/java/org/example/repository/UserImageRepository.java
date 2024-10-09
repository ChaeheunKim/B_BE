package org.example.repository;


import org.example.entity.User;
import org.example.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    UserImage findByUser(User user);
}
