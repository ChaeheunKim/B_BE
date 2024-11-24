package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.Networking;
import org.example.domain.post.Entity.NetworkingImage;
import org.example.domain.post.Repository.NetworkingImageRepository;
import org.example.domain.post.Repository.NetworkingRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception400;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
            int imgThumbnail_id = requestDTO.getImgThumbnail_id();
            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            List<NetworkingImage> imageEntities = postImageService.uploadNetworkingImages(images, imgThumbnail_id);
            Networking networking1 = new Networking(requestDTO, imageEntities);
            networkingRepository.save(networking1 );
            return true;
        }
        catch(Exception e){
            throw new Exception400(null, ErrorCode.UNSUPPORTED_MEDIA_TYPE);

        }

    }

    //네트워킹 게시글 수정
    public  boolean NetworkingUpdatePost(int Id, PostRequestDTO requestDTO, List<MultipartFile> images) throws IllegalAccessException {
        int imgThumbnail_id = requestDTO.getImgThumbnail_id();
        List<NetworkingImage> imageEntities = postImageService.uploadNetworkingImages(images, imgThumbnail_id);
        boolean success = false;
        try {
            Networking networking = networkingRepository.findById(Id);
            networking.update(requestDTO, imageEntities);
            success = true;
        }
        catch(Exception e) {
            success = false;
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //네트워킹 게시글 가져오기
    public void getNetworkingList() {
        List<Networking> networkingList = networkingRepository.findAll();
        List<PostResponseDTO.NetworkingandSeminarItem> networkingItemList = new ArrayList<>();
        for (Networking networking : networkingList) {
            NetworkingImage image = networkingImageRepository.findBythumbnailTrue();
            PostResponseDTO.NetworkingandSeminarItem postResponseDTO = new PostResponseDTO.NetworkingandSeminarItem(networking.getTitle(), image.getUrl(), networking.getPeriod());
            networkingItemList.add(postResponseDTO);
        }

    }

    //네트워킹 게시글 삭제
    public boolean deleteNetworkingPost(int Id) {
        boolean success = false;
        try {

            Networking networking = networkingRepository.findById(Id);
            networkingRepository.delete(networking);
            List<NetworkingImage> images = networkingImageRepository.findByNetworkingId(Id);
            commonService.deleteImagesFromS3(images);
            return true;


        } catch (Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }
    }

}
