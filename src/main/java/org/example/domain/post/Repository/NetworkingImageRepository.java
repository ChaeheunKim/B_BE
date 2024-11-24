package org.example.domain.post.Repository;

import org.example.domain.post.Entity.NetworkingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface NetworkingImageRepository extends JpaRepository<NetworkingImage, Long> {


    Optional<NetworkingImage> findById(int id);

    List<NetworkingImage> findByNetworkingId(int networkingId);

    NetworkingImage findBythumbnailTrue();
}
