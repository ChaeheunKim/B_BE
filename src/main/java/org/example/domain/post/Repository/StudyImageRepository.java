package org.example.domain.post.Repository;


import org.example.domain.post.Entity.Seminar;
import org.example.domain.post.Entity.SeminarImage;
import org.example.domain.post.Entity.Study;
import org.example.domain.post.Entity.StudyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudyImageRepository extends JpaRepository<StudyImage, Long> {


    List<StudyImage> findByStudy(Study study);
}
