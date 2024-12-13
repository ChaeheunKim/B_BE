package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostDetailResponseDTO;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.Networking;
import org.example.domain.post.Entity.NetworkingImage;
import org.example.domain.post.Entity.Study;
import org.example.domain.post.Repository.NetworkingImageRepository;
import org.example.domain.post.Repository.NetworkingRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NetworkingService {

    private final PostImageService postImageService;
    private final NetworkingRepository networkingRepository;
    private final NetworkingImageRepository networkingImageRepository;
    private final CommonService commonService;

    //네트워킹 게시글 등록
    public  boolean createNetworkingPost(PostRequestDTO requestDTO, List<MultipartFile> images) {

        try {
            Networking networking = new Networking(requestDTO);
            networkingRepository.save(networking);
            int imgThumbnail_id = requestDTO.getImgThumbnail_id();
            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            List<NetworkingImage> imageEntities = postImageService.uploadNetworkingImages(networking.getId(),images, imgThumbnail_id);
            networking.setImages(imageEntities);
            networkingRepository.save(networking);
            return true;
        }
        catch(Exception e){
            throw new RuntimeException(e);

        }

    }

    //네트워킹 게시글 수정
    public  boolean NetworkingUpdatePost(int Id, PostRequestDTO requestDTO, List<MultipartFile> images)  {
        int imgThumbnail_id = requestDTO.getImgThumbnail_id();
        List<NetworkingImage> imageEntities = postImageService.uploadNetworkingImages(Id,images, imgThumbnail_id);
        boolean success = false;
        try {
            Optional<Networking> networking = networkingRepository.findById(Id);
            networking.get().update(requestDTO, imageEntities);
            networkingRepository.save(networking.get());
            success = true;
        }
        catch(Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //네트워킹 게시글 가져오기
    public List<PostResponseDTO.NetworkingandSeminarItem> getNetworkingList() {
        return networkingRepository.findAll().stream()
                .flatMap(networking ->
                        networkingImageRepository.findByNetworking(networking).stream()
                                .filter(NetworkingImage::isThumbnail)
                                .map(networkingImage -> new PostResponseDTO.NetworkingandSeminarItem(
                                        networking.getTitle(),
                                        networkingImage.getUrl(),
                                        networking.getPeriod()
                                ))
                )
                .collect(Collectors.toList());
    }

    //네트워킹 게시글 삭제
    public boolean deleteNetworkingPost(int Id) {
        boolean success = false;
        try {
            Optional<Networking> networking = networkingRepository.findById(Id);
            List<NetworkingImage> images = networkingImageRepository.findByNetworking(networking.get());
            networkingRepository.delete(networking.get());
            commonService.deleteImagesFromS3(images);
            networkingImageRepository.deleteAll(images);
            return true;


        } catch (Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }
    }

    //네트워킹 게시글 세부정보 조히
    public PostDetailResponseDTO detailNetworkingPost(int post_id){
        Networking networking = networkingRepository.findById(post_id)
                .orElseThrow(() -> new Exception404(null, ErrorCode.NOT_FOUND_POST));

        List<String> imageUrls = networkingImageRepository.findByNetworking(networking).stream()
                .map(projectImage -> projectImage.getUrl())
                .collect(Collectors.toList());

        return new PostDetailResponseDTO(networking.getTitle(),networking.getContent(), networking.getPeriod(), imageUrls, networking.getParticipant(), null, null);


    }

}
