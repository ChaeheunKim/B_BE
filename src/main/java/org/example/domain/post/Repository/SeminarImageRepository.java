package org.example.domain.post.Repository;

import org.example.domain.post.Entity.ProjectImage;
import org.example.domain.post.Entity.Seminar;
import org.example.domain.post.Entity.SeminarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeminarImageRepository extends JpaRepository<SeminarImage, Long> {

    List<SeminarImage> findBySeminarId(int seminarId);

    // 새로운 메서드 추가
    List<SeminarImage> findBy_IdAndImgThumbnailTrue(int Id);

    Optional<SeminarImage> findById(int Id);

    SeminarImage findBythumbnailTrue();


}
