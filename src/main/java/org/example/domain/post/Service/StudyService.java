package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.Study;
import org.example.domain.post.Entity.StudyImage;
import org.example.domain.post.Repository.StudyImageRepository;
import org.example.domain.post.Repository.StudyRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception400;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final PostImageService postImageService;
    private final StudyRepository studyRepository;
    private final StudyImageRepository studyImageRepository;
    private final CommonService commonService;

    //스터디 게시글 등록
    public  boolean createStudyPost(PostRequestDTO requestDTO, List<MultipartFile> images) {

        try {
            int imgThumbnail_id = requestDTO.getImgThumbnail_id();
            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            List<StudyImage> imageEntities = postImageService.uploadStudyImages(images, imgThumbnail_id);
            Study study1 = new Study(requestDTO, imageEntities);
            studyRepository.save(study1);
            return true;
        }
        catch(Exception e){
            throw new Exception400(null, ErrorCode.UNSUPPORTED_MEDIA_TYPE);

        }

    }

    //스터디 게시글 수정
    public  boolean StudyUpdatePost(int Id, PostRequestDTO postRequestDTO, List<MultipartFile> images) throws IllegalAccessException {
        int imgThumbnail_id = postRequestDTO.getImgThumbnail_id();
        List<StudyImage> imageentities = postImageService.uploadStudyImages(images, imgThumbnail_id);
        boolean success = false;
        try {
            Study study = studyRepository.findById(Id);
            study.update(postRequestDTO, imageentities);
            success = true;
        }
        catch(Exception e) {
            success = false;
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //스터디 게시글 가져오기
    public void getStudyList() {
        List<Study> studyList = studyRepository.findAll();
        List<PostResponseDTO.StudyItem> studyItemList = new ArrayList<>();
        for (Study study : studyList) {
            StudyImage image = studyImageRepository.findBythumbnailTrue();
            PostResponseDTO.StudyItem postResponseDTO = new PostResponseDTO.StudyItem(study.getTitle(), image.getUrl(), study.getPeriod(),study.getPart());
            studyItemList.add(postResponseDTO);
        }

    }


    //스터디 게시글 삭제
    public boolean deleteStudyPost(int Id) {
        boolean success = false;
        try {

            Study study = studyRepository.findById(Id);
            studyRepository.delete(study);
            List<StudyImage> images = studyImageRepository.findByStudyId(Id);
            commonService.deleteImagesFromS3(images);
            studyImageRepository.deleteAll(images);
            return true;

        } catch (Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }
    }

}