package org.example.user.UserRepository;

import org.example.user.UserEntity.State;
import org.example.user.UserEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, User> {

    Optional<User> findByEmail(String email);
    List<User> findByState(State state);

    User findById(Long user_id);




}
