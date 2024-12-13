package org.example.domain.post.Repository;

import org.example.domain.post.Entity.Networking;
import org.example.domain.post.Entity.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NetworkingRepository extends JpaRepository<Networking, Integer> {





}
