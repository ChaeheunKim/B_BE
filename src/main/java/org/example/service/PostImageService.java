package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.repository.PostImageRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserImageRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final PostRepository postRepository;

    private final S3Client s3Client; // AWSConfig를 통해 주입된 S3Client


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * 여러 이미지를 S3에 업로드하고 DB에 저장
     *
     * @param post   - 해당 게시물 정보
     * @param images - 업로드할 이미지 파일 리스트
     *               //     * @param thumbnailNumber - 썸네일로 지정할 이미지 인덱스
     */
    public void uploadPostImages(Post post, List<MultipartFile> images, boolean imgThumbnail) {
        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);

            // 이미지 파일을 S3에 저장하고 URL 반환
            String s3ImageUrl = saveImageToS3(image);

            // 이미지 메타데이터 설정
            String imgName = image.getName();

            // Image 엔티티 생성 및 저장
            PostImage postImage = new PostImage(post, imgName, s3ImageUrl, imgThumbnail);
            postImageRepository.save(postImage);
        }
    }

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


//    public void uploadUserImages(Post post, List<MultipartFile> images) {
//        for (int i = 0; i < images.size(); i++) {
//            MultipartFile image = images.get(i);
//
//            // 이미지 파일을 S3에 저장하고 URL 반환
//            String s3ImageUrl = saveImageToS3(image);
//
//            // 이미지 메타데이터 설정
//            String imgName = image.getName();
//
//            // Image 엔티티 생성 및 저장
//            Image newImage = new Image(post, imgName, s3ImageUrl, imgThumbnail);
//            userImageRepository.save(newImage);
//        }
//    }

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
