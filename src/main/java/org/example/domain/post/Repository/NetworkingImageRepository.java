package org.example.domain.post.Repository;

import org.example.domain.post.Entity.Networking;
import org.example.domain.post.Entity.NetworkingImage;
import org.example.domain.post.Entity.Seminar;
import org.example.domain.post.Entity.SeminarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface NetworkingImageRepository extends JpaRepository<NetworkingImage, Long> {


    List<NetworkingImage> findByNetworking(Networking networking);
}
