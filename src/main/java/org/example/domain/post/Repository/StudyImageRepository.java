package org.example.domain.post.Repository;


import org.example.domain.post.Entity.StudyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudyImageRepository extends JpaRepository<StudyImage, Long> {



    Optional<StudyImage> findById(int id);

    List<StudyImage> findByStudyId(int studyId);

    StudyImage findBythumbnailTrue();
}
