package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.Entity.*;
import org.example.domain.post.Repository.*;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserEntity.UserImage;
import org.example.domain.user.UserRepository.UserImageRepository;
import org.example.domain.user.UserRepository.UserRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.*;



@Service
@RequiredArgsConstructor
public class PostImageService {

    private final ProjectImageRepository projectImageRepository;
    private final SeminarImageRepository seminarImageRepository;
    private final NetworkingImageRepository networkingImageRepository;
    private final StudyImageRepository studyImageRepository;
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final S3Client s3Client;
    private final ProjectRepository projectRepository;
    private final SeminarRepository seminarRepository;
    private final StudyRepository studyRepository;
    private final NetworkingRepository networkingRepository;



    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    //프로젝트 이미지 업로드
    public List<ProjectImage> uploadProjectImages(int project_id, List<MultipartFile> images, int imgThumbnail_id){
        List<ProjectImage> createdEntities = new ArrayList<>();
        //썸네일 이미지 구분 변수
        int index = 1;

        // project_id를 통해 Project 엔티티를 조회
        Optional<Project> project = projectRepository.findById(project_id);

        for (MultipartFile image : images) {
            String s3ImageUrl = saveImageToS3(image);
            String imgName = image.getOriginalFilename();

            // imgThumbnail_id와 index를 비교하여 썸네일 여부 결정
            boolean imgThumbnail = (index == imgThumbnail_id);
            ProjectImage projectImageEntity = new ProjectImage(imgName, s3ImageUrl, imgThumbnail, project.get());
            projectImageRepository.save(projectImageEntity);
            createdEntities.add(projectImageEntity);

            index++;
        }
        return createdEntities;

    }

    //세미나 이미지 업로드
    public List<SeminarImage> uploadSeminarImages(int seminar_id,List<MultipartFile> images, int imgThumbnail_id){
        List<SeminarImage> createdEntities = new ArrayList<>();
        int index = 1;
        Optional<Seminar> seminar = seminarRepository.findById(seminar_id);
        for (MultipartFile image : images) {
            String s3ImageUrl = saveImageToS3(image);
            String imgName = image.getOriginalFilename();
            boolean imgThumbnail = (index == imgThumbnail_id);

            SeminarImage SeminarImageEntity=new SeminarImage(imgName,s3ImageUrl,imgThumbnail,seminar.get());
            seminarImageRepository.save(SeminarImageEntity);
            createdEntities.add(SeminarImageEntity);

            index++;
        }
        return createdEntities;

    }

    //스터디 이미지 업로드
    public List<StudyImage> uploadStudyImages(int study_id,List<MultipartFile> images, int imgThumbnail_id){
        List<StudyImage> createdEntities = new ArrayList<>();
        int index = 1;
        Optional<Study> study=studyRepository.findById(study_id);
        for (MultipartFile image : images) {
            String s3ImageUrl = saveImageToS3(image);
            String imgName = image.getOriginalFilename();
            boolean imgThumbnail = (index == imgThumbnail_id);

            StudyImage StudyImageEntity=new StudyImage(imgName,s3ImageUrl,imgThumbnail,study.get());
            studyImageRepository.save(StudyImageEntity);
            createdEntities.add(StudyImageEntity);

            index++;
        }
        return createdEntities;

    }

    //네트워킹 이미지 업로드
    public List<NetworkingImage> uploadNetworkingImages(int networking_id,List<MultipartFile> images, int imgThumbnail_id){
        List<NetworkingImage> createdEntities = new ArrayList<>();
        int index = 1;
        Optional<Networking> networking=networkingRepository.findById(networking_id);

        for (MultipartFile image : images) {
            String s3ImageUrl = saveImageToS3(image);
            String imgName = image.getOriginalFilename();
            boolean imgThumbnail = (index == imgThumbnail_id);

            NetworkingImage NetworkingImageEntity = new NetworkingImage(imgName,s3ImageUrl,imgThumbnail,networking.get());
            networkingImageRepository.save(NetworkingImageEntity);
            createdEntities.add(NetworkingImageEntity);

            index++;
        }
        return createdEntities;

    }



    //유저 이미지 업로드
    public void uploadUserImages(MultipartFile image, Long user_id) {
        User user = userRepository.findById(user_id);

        // 이미지 파일을 S3에 저장하고 URL 반환
        String s3ImageUrl = saveImageToS3(image);
        System.out.println(s3ImageUrl);

        // 이미지 메타데이터 설정
        String imgName = image.getName();
        System.out.println(imgName);

        // Image 엔티티 생성 및 저장
        UserImage userImage = new UserImage(user, s3ImageUrl, imgName);
        userImageRepository.save(userImage);

    }


        /**
         * S3에 이미지 파일 저장 메소드
         * @param image - 업로드할 이미지 파일
         * @return - 업로드된 이미지의 S3 URL 반환
         */
    private String saveImageToS3(MultipartFile image) {
        // S3에 저장할 파일 이름 생성 (고유한 이름 보장을 위해 UUID 사용)
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        String s3Key = "uploads/images/" + fileName; // S3 버킷 내 파일 경로 지정

        try {
            // S3에 파일 업로드 요청 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            // 이미지 파일을 S3에 업로드 (이미지를 바이트 배열로 변환하여 전송)
            s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(image.getBytes()));
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(s3Key)).toExternalForm();

        } catch (IOException e) {
            // 업로드 실패 시 예외 발생
            throw new RuntimeException("Failed to upload image to S3", e);
        }
    }
}
