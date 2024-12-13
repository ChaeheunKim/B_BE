package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostDetailResponseDTO;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.Project;
import org.example.domain.post.Entity.Study;
import org.example.domain.post.Entity.StudyImage;
import org.example.domain.post.Repository.StudyImageRepository;
import org.example.domain.post.Repository.StudyRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Study study = new Study(requestDTO);
            studyRepository.save(study);
            int imgThumbnail_id = requestDTO.getImgThumbnail_id();
            List<StudyImage> imageEntities = postImageService.uploadStudyImages(study.getId(),images, imgThumbnail_id);
            study.setImages(imageEntities);
            studyRepository.save(study);
            return true;
        }
        catch(Exception e){
            throw new RuntimeException(e);

        }

    }

    //스터디 게시글 수정
    public  boolean StudyUpdatePost(int Id, PostRequestDTO postRequestDTO, List<MultipartFile> images)  {
        int imgThumbnail_id = postRequestDTO.getImgThumbnail_id();
        List<StudyImage> imageentities = postImageService.uploadStudyImages(Id,images, imgThumbnail_id);
        boolean success = false;
        try {
            Optional<Study> study = studyRepository.findById(Id);
            study.get().update(postRequestDTO, imageentities);
            studyRepository.save(study.get());
            success = true;
        }
        catch(Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //스터디 게시글 가져오기
    public List<PostResponseDTO.StudyItem> getStudyList() {
        return studyRepository.findAll().stream()
                .flatMap(study ->
                        studyImageRepository.findByStudy(study).stream()
                                .filter(StudyImage::isThumbnail)
                                .map(studyImage -> new PostResponseDTO.StudyItem(
                                        study.getTitle(),
                                        studyImage.getUrl(),
                                        study.getPeriod(),
                                        study.getPart()
                                ))
                )
                .collect(Collectors.toList());
    }



    //스터디 게시글 삭제
    public boolean deleteStudyPost(int Id) {
        boolean success = false;
        try {

            Optional<Study> study = studyRepository.findById(Id);
            List<StudyImage> images = studyImageRepository.findByStudy(study.get());
            studyRepository.delete(study.get());
            commonService.deleteImagesFromS3(images);
            studyImageRepository.deleteAll(images);
            return true;

        } catch (Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }



    }

    //스터디 게시글 세부정보 조히
    public PostDetailResponseDTO detailStudyPost(int post_id){
        Study study = studyRepository.findById(post_id)
                .orElseThrow(() -> new Exception404(null, ErrorCode.NOT_FOUND_POST));

        List<String> imageUrls = studyImageRepository.findByStudy(study).stream()
                .map(projectImage -> projectImage.getUrl())
                .collect(Collectors.toList());

        return new PostDetailResponseDTO(study.getTitle(), study.getContent(), study.getPeriod(), imageUrls, study.getParticipant(), study.getPart(), null);


    }

}