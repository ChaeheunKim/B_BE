package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostDetailResponseDTO;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.Seminar;
import org.example.domain.post.Entity.SeminarImage;
import org.example.domain.post.Repository.SeminarImageRepository;
import org.example.domain.post.Repository.SeminarRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            List<SeminarImage> imageEntities = postImageService.uploadSeminarImages(seminar.getId(),images, imgThumbnail_id);
            seminar.setImages(imageEntities);
            seminarRepository.save(seminar);
            return true;
        }
        catch(Exception e){
            throw new RuntimeException(e);

        }

    }

    //세미나 게시글 수정
    public  boolean SeminarUpdatePost(int Id, PostRequestDTO postRequestDTO, List<MultipartFile> images) {
        int imgThumbnail_id = postRequestDTO.getImgThumbnail_id();
        List<SeminarImage> imageentities = postImageService.uploadSeminarImages(Id,images, imgThumbnail_id);
        boolean success = false;
        try {
            Optional<Seminar> seminar = seminarRepository.findById(Id);
            seminar.get().update(postRequestDTO, imageentities);
            seminarRepository.save(seminar.get());
            success = true;
        }
        catch(Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //세미나 게시글 조회
    public List<PostResponseDTO.NetworkingandSeminarItem> getSeminarList() {
        return seminarRepository.findAll().stream()
                .flatMap(seminar ->
                        seminarImageRepository.findBySeminar(seminar).stream()
                                .filter(SeminarImage::isThumbnail)
                                .map(seminarImage -> new PostResponseDTO.NetworkingandSeminarItem(
                                        seminar.getTitle(),
                                        seminarImage.getUrl(),
                                        seminar.getPeriod()
                                ))
                )
                .collect(Collectors.toList());
    }


    //세미나 게시글 삭제
    public boolean deleteSeminarPost(int Id) {
        boolean success = false;
        try {

            Optional<Seminar> seminar = seminarRepository.findById(Id);
            List<SeminarImage> images = seminarImageRepository.findBySeminar(seminar.get());
            seminarRepository.delete(seminar.get());
            commonService.deleteImagesFromS3(images);
            seminarImageRepository.deleteAll(images);
            return true;

        } catch (Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }
    }

    //세미나 게시글 세부정보 조회
    public PostDetailResponseDTO detailSeminarPost(int post_id){
        Seminar seminar = seminarRepository.findById(post_id)
                .orElseThrow(() -> new Exception404(null, ErrorCode.NOT_FOUND_POST));

        List<String> imageUrls = seminarImageRepository.findBySeminar(seminar).stream()
                .map(projectImage -> projectImage.getUrl())
                .collect(Collectors.toList());

        return new PostDetailResponseDTO(seminar.getTitle(), seminar.getContent(), seminar.getPeriod(), imageUrls, seminar.getParticipant(), null, null);


    }

}
