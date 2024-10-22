package org.example.post.PostRepository;

import org.example.post.PostEntity.Category;
import org.example.post.PostEntity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByCategory(Category category);
    Optional<Post> findById(int postid);

//    User findByUser_id(String user_id);
}
