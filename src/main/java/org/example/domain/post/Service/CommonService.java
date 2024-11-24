package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommonService {

    private final S3Client s3Client;
    private final PostImageService postImageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketname;



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

    //참여자 처리
    public String toString(List<Map<String,Object>> participant){
        if (participant == null || participant.isEmpty()) {
            return "";
        }
        return participant.stream()
                .map(map -> (String) map.get("name"))
                .filter(name -> name != null && !name.isEmpty())
                .collect(Collectors.joining(","));
    }




}





