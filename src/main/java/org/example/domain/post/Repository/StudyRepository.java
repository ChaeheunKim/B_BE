package org.example.domain.post.Repository;

import org.example.domain.post.Entity.Seminar;
import org.example.domain.post.Entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Integer> {





}
