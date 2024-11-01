package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.Entity.*;
import org.example.domain.post.Repository.NetworkingImageRepository;
import org.example.domain.post.Repository.ProjectImageRepository;
import org.example.domain.post.Repository.SeminarImageRepository;
import org.example.domain.post.Repository.StudyImageRepository;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserEntity.UserImage;
import org.example.domain.user.UserRepository.UserImageRepository;
import org.example.domain.user.UserRepository.UserRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception400;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final ProjectImageRepository projectImageRepository;
    private final SeminarImageRepository seminarImageRepository;
    private final NetworkingImageRepository networkingImageRepository;
    private  final StudyImageRepository studyImageRepository;
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final S3Client s3Client;
    private final NetworkingImage networkingImage;
    private final ProjectImage projectImage;
    private  final SeminarImage seminarImage;
    private final StudyImage studyImage;


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    //게시글 이미지 업로드
    public <T> List<Object> uploadPostImages(T entity, List<MultipartFile> images, boolean imgThumbnail) {
        List<Object> createdEntities = new ArrayList<>();

        for (MultipartFile image : images) {
            String s3ImageUrl = saveImageToS3(image);
            String imgName = image.getOriginalFilename();
            Object savedImageEntity;
            // Image 엔티티 생성 및 저장
            if (entity instanceof ProjectImage) {
                projectImage.toEntity((Project) entity, imgName,s3ImageUrl,imgThumbnail);
                savedImageEntity=projectImageRepository.save(projectImage);
            } else if (entity instanceof SeminarImage) {
                seminarImage.toEntity((Seminar) entity, imgName,s3ImageUrl,imgThumbnail);
                savedImageEntity=seminarImageRepository.save(seminarImage);
            } else if (entity instanceof StudyImage) {
                studyImage.toEntity((Study) entity, imgName,s3ImageUrl,imgThumbnail);
                savedImageEntity=studyImageRepository.save(studyImage);
            } else if (entity instanceof NetworkingImage) {
                networkingImage.toEntity((Networking) entity,imgName,s3ImageUrl,imgThumbnail);
                savedImageEntity=studyImageRepository.save(studyImage);
            } else {
                throw new Exception400(null, ErrorCode.UNSUPPORTED_MEDIA_TYPE);
            }
            createdEntities.add(savedImageEntity);
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
