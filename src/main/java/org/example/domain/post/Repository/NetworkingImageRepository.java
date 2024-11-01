package org.example.domain.post.Repository;

import org.example.domain.post.Entity.Networking;
import org.example.domain.post.Entity.NetworkingImage;
import org.example.domain.post.Entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NetworkingImageRepository extends JpaRepository<NetworkingImage, Long> {


    List<NetworkingImage> findBy_IdAndImgThumbnailTrue(int Id);

    Optional<NetworkingImage> findById(int Id);

    List<NetworkingImage> findByNetworkingId(int networkingId);

    NetworkingImage findBythumbnailTrue();
}
