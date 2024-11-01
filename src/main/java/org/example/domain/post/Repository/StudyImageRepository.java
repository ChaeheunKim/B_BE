package org.example.domain.post.Repository;

import org.example.domain.post.Entity.ProjectImage;
import org.example.domain.post.Entity.Study;
import org.example.domain.post.Entity.StudyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyImageRepository extends JpaRepository<StudyImage, Long> {

    List<StudyImage> findBy_IdAndImgThumbnailTrue(int Id);

    Optional<StudyImage> findById(int Id);

    List<StudyImage> findByStudyId(int studyId);

    StudyImage findBythumbnailTrue();
}
