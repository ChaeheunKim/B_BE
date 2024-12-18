package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.Entity.Networking;
import org.example.domain.post.Entity.Project;
import org.example.domain.post.Entity.Seminar;
import org.example.domain.post.Entity.Study;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommonService {

    private final S3Client s3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;






    // S3 및 DB에서 이미지 삭제
    void deleteImagesFromS3(List<?> images) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Object image : images) {
            Method getImgUrlMethod = image.getClass().getMethod("getUrl");
            String imageUrl = (String) getImgUrlMethod.invoke(image);
            String path = imageUrl.substring(imageUrl.indexOf("uploads/"));
            String keyPath = path.substring(0, path.lastIndexOf('_') + 1);
            Method getImgNameMethod = image.getClass().getMethod("getName");
            String imgName = (String) getImgNameMethod.invoke(image);
            String deleteKey = keyPath + imgName;
            // S3에서 이미지 삭제
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(deleteKey)
                    .build());
        }
    }


    //s3에 저장
    public String saveImageToS3(MultipartFile image) {
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





