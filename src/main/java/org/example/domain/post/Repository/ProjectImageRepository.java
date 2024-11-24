package org.example.domain.post.Repository;

import org.example.domain.post.Entity.Project;
import org.example.domain.post.Entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {


    List<ProjectImage> findByProject(Project project);




}
