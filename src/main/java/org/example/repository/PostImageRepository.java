package org.example.repository;

import org.example.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

//    List<PostImage> findByPost_postId(int post_id);

    // 새로운 메서드 추가
    List<PostImage> findByPost_postIdAndImgThumbnailTrue(int postId);

    List<PostImage> findByPost_postId(int postId);
}
