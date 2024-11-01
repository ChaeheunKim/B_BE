package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.*;
import org.example.domain.post.Repository.*;
import org.example.domain.user.UserRepository.UserImageRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception400;
import org.example.global.errors.exception.Exception404;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
//TO DO: 서비스 나눠보기

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserImageRepository userImageRepository;
    private final S3Client s3Client;
    private final PostImageService postImageService;
    private final ProjectRepository projectRepository;
    private final SeminarRepository seminarRepository;
    private final StudyRepository studyRepository;
    private final NetworkingRepository networkingRepository;
    private final Networking networking;
    private final Seminar seminar;
    private final Study study;
    private final Project project;
    private final ProjectImageRepository projectImageRepository;
    private final SeminarImageRepository seminarImageRepository;
    private final NetworkingImageRepository networkingImageRepository;
    private final StudyImageRepository studyImageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketname;

    //게시글 등록
    public <T> boolean createPost(T entity, PostRequestDTO requestDTO, List<MultipartFile> images) {

        try {
            boolean imgThumbnail = requestDTO.isImgThumbnail();
            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            List<Object> imageEntities = postImageService.uploadPostImages(entity, images, imgThumbnail);
            // Image 엔티티 생성 및 저장(엔티티에 따라서 다른 레포에 저장)
            if (entity instanceof Project) {
                project.toEntity(requestDTO, imageEntities);
                projectRepository.save(project);
            } else if (entity instanceof Networking) {
                networking.toEntity(requestDTO, imageEntities);
                networkingRepository.save(networking);
            } else if (entity instanceof Seminar) {
                seminar.toEntity(requestDTO, imageEntities);
                seminarRepository.save(seminar);
            } else if (entity instanceof Study) {
                study.toEntity(requestDTO, imageEntities);
                studyRepository.save(study);
            } else {
                throw new Exception400(null, ErrorCode.UNSUPPORTED_MEDIA_TYPE);
            }
            return true;
        } catch (Exception e) {
            return false; // 어떤 이유로든 실패하면 false 반환
        }

    }

    //유저이미지 등록
    public boolean createUserProfile(MultipartFile image, Long user_id) {

        try {
            postImageService.uploadUserImages(image, user_id);
            return true;
        } catch (Exception e) {
            // 예외 처리 로직 (로깅 등)
            return false; // 어떤 이유로든 실패하면 false 반환
        }

    }


    //게시글 수정
    public <T> boolean UpdatePost(int Id, PostRequestDTO postRequestDTO, List<MultipartFile> images, T entity) throws IllegalAccessException {
        boolean imgThumbnail = postRequestDTO.isImgThumbnail();
        List<Object> imageentities = postImageService.uploadPostImages(entity, images, imgThumbnail);
        boolean success = false;
        // 해당하는 게시글 레포에서 찾기
        if (entity instanceof Project) {
            Project project = projectRepository.findById(Id);
            project.update(postRequestDTO, imageentities);
        } else if (entity instanceof Seminar) {
            Seminar seminar = seminarRepository.findById(Id);
            seminar.update(postRequestDTO, imageentities);
        } else if (entity instanceof Networking) {
            Networking networking = networkingRepository.findById(Id);
            networking.update(postRequestDTO, imageentities);
        } else if (entity instanceof Study) {
            Study study = studyRepository.findById(Id);
            study.update(postRequestDTO, imageentities);
        } else {
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //TO DO : 카테고리 별로 게시글 가져오기


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

    //세미나 게시글 가져오기
    public void getSeminarList() {
        List<Seminar> seminarList = seminarRepository.findAll();
        List<PostResponseDTO.NetworkingandSeminarItem> seminarItemList = new ArrayList<>();
        for (Seminar seminar : seminarList) {
            SeminarImage image = seminarImageRepository.findBythumbnailTrue();
            PostResponseDTO.NetworkingandSeminarItem postResponseDTO = new PostResponseDTO.NetworkingandSeminarItem(seminar.getTitle(), image.getUrl(), seminar.getPeriod());
            seminarItemList.add(postResponseDTO);
        }
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

    //게시글 삭제
    public <T> boolean deletePost(T entity, int Id) {
        boolean success = false;
        try {
            if (entity instanceof Project) {
                Project project = projectRepository.findById(Id);
                projectRepository.delete(project);
                List<ProjectImage> images = projectImageRepository.findByProjectId(Id);
                deleteImagesFromS3(images);
                projectImageRepository.deleteAll(images);
            } else if (entity instanceof Seminar) {
                Seminar seminar = seminarRepository.findById(Id);
                seminarRepository.delete(seminar);
                List<SeminarImage> images = seminarImageRepository.findBySeminarId(Id);
                deleteImagesFromS3(images);
                seminarImageRepository.deleteAll(images);
            } else if (entity instanceof Networking) {
                Networking networking = networkingRepository.findById(Id);
                networkingRepository.delete(networking);
                List<NetworkingImage> images = networkingImageRepository.findByNetworkingId(Id);
                deleteImagesFromS3(images);
                networkingImageRepository.deleteAll(images);
            } else if (entity instanceof Study) {
                Study study = studyRepository.findById(Id);
                studyRepository.delete(study);
                List<StudyImage> images = studyImageRepository.findByStudyId(Id);
                deleteImagesFromS3(images);
                studyImageRepository.deleteAll(images);
            } else {
                throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    // S3 및 DB에서 이미지 삭제
    void deleteImagesFromS3(List<?> images) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Object image : images) {
            Method getImgUrlMethod = image.getClass().getMethod("getImgUrl");
            String imageUrl = (String) getImgUrlMethod.invoke(image);
            String path = imageUrl.substring(imageUrl.indexOf("uploads/"));
            String keyPath = path.substring(0, path.lastIndexOf('_') + 1);
            Method getImgNameMethod = image.getClass().getMethod("getImgName");
            String imgName = (String) getImgNameMethod.invoke(image);
            String deleteKey = keyPath + imgName;

            // S3에서 이미지 삭제
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketname)
                    .key(deleteKey)
                    .build());
        }
    }



}





