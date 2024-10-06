package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Image;
import org.example.entity.Post;
import org.example.repository.ImageRepository;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    /*
    * 사진 등록
    * @param postId
    * @param images
    * */
    public void uploadImages(Post post, List<MultipartFile> images, int thumbnail_number){
        try {
            // 이미지 파일 저장 경로 설정
            String uploadsDir = "src/main/resources/static/uploads/images/";

            if(images != null) {
                if (!images.get(0).isEmpty()) { // image가 없으면 이미지 저장x
                    // 각 이미지 파일 업로드 및 DB 저장
                    for (int i = 0; i < images.size(); i++) {
                        MultipartFile image = images.get(i);

                        // 이미지 파일 경로 저장
                        String dbFilePath = saveImage(image, uploadsDir);

                        // 속성 생성
                        String img_name = image.getName();
                        Long img_size = image.getSize();
                        String img_originName = image.getOriginalFilename();
                        String img_ext = StringUtils.getFilenameExtension(image.getOriginalFilename());

                        // 썸네일 설정
                        char img_thumbnail = (i == thumbnail_number - 1) ? 'Y' : 'N';

                        // Image 엔티티 생성 및 저장
                        Image newImage = new Image(post, img_name, dbFilePath, img_originName, img_size, img_ext, img_thumbnail);
                        imageRepository.save(newImage);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 이미지 파일 저장 메소드
    private String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();

        // 실제 파일이 저장될 경로
        String filePath = uploadsDir + fileName;

        // DB에 저장할 경로 문자열
        String dbFilePath = "/uploads/images/" + fileName;

        Path path = Paths.get(filePath); // Path 객체 생성
        Files.createDirectories(path.getParent()); // 디렉토리 생성
        Files.write(path, image.getBytes()); // 디렉토리에 파일 저장

        return dbFilePath;

    }



}
