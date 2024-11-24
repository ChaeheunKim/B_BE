package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.Project;
import org.example.domain.post.Entity.Seminar;
import org.example.domain.post.Entity.SeminarImage;
import org.example.domain.post.Repository.SeminarImageRepository;
import org.example.domain.post.Repository.SeminarRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception400;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeminarService {

    private final PostImageService postImageService;
    private final SeminarRepository seminarRepository;
    private final SeminarImageRepository seminarImageRepository;
    private final CommonService commonService;

    //세미나 게시글 등록
    public  boolean createSeminarPost(PostRequestDTO requestDTO, List<MultipartFile> images) {

        try {
            Seminar seminar = new Seminar(requestDTO);
            seminarRepository.save(seminar);
            int imgThumbnail_id = requestDTO.getImgThumbnail_id();
            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            List<SeminarImage> imageEntities = postImageService.uploadSeminarImages(images, imgThumbnail_id);
            seminar.setImages(imageEntities);
            seminarRepository.save(seminar);
            return true;
        }
        catch(Exception e){
            throw new RuntimeException(e);

        }

    }

    //세미나 게시글 수정
    public  boolean SeminarUpdatePost(int Id, PostRequestDTO postRequestDTO, List<MultipartFile> images) throws IllegalAccessException {
        int imgThumbnail_id = postRequestDTO.getImgThumbnail_id();
        List<SeminarImage> imageentities = postImageService.uploadSeminarImages(images, imgThumbnail_id);
        boolean success = false;
        try {
            Seminar semnar = seminarRepository.findById(Id);
            semnar.update(postRequestDTO, imageentities);
            success = true;
        }
        catch(Exception e) {
            success = false;
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //세미나 게시글 조회
    public List<PostResponseDTO.NetworkingandSeminarItem> getSeminarList() {
        List<Seminar> seminarList = seminarRepository.findAll();
        List<PostResponseDTO.NetworkingandSeminarItem> seminarItemList = new ArrayList<>();
        for (Seminar seminar : seminarList) {
            SeminarImage image = seminarImageRepository.findBythumbnailTrue();
            PostResponseDTO.NetworkingandSeminarItem postResponseDTO = new PostResponseDTO.NetworkingandSeminarItem(seminar.getTitle(), image.getUrl(), seminar.getPeriod());
            seminarItemList.add(postResponseDTO);
        }
        return seminarItemList;
    }


    //세미나 게시글 삭제
    public boolean deleteSeminarPost(int Id) {
        boolean success = false;
        try {

            Seminar seminar = seminarRepository.findById(Id);
            seminarRepository.delete(seminar);
            List<SeminarImage> images = seminarImageRepository.findBySeminarId(Id);
            commonService.deleteImagesFromS3(images);
            seminarImageRepository.deleteAll(images);
            return true;

        } catch (Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }
    }

}

