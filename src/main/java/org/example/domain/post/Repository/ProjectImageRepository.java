package org.example.domain.post.Repository;

import org.example.domain.post.Entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {


    List<ProjectImage> findBy_IdAndImgThumbnailTrue(int Id);
    ProjectImage findBythumbnailTrue();

    Optional<ProjectImage> findById(int Id);
    List<ProjectImage> findByProjectId(int projectId);

}
