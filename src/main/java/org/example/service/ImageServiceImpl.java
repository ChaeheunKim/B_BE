package org.example.service;

import org.example.dto.ImageResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public ImageResponseDTO uploadImage(Long userId, MultipartFile image) {
        // 응답 객체 초기화
        ImageResponseDTO responseDTO = new ImageResponseDTO();
        responseDTO.setStatus("success");

        try {
            // 파일 저장 경로 설정 (예: C:/uploads/user_{userId}/)
            String directoryPath = "C:/uploads/user_" + userId + "/";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();  // 디렉토리가 없을 경우 생성
            }

            // 파일 저장 위치 지정
            String filePath = directoryPath + image.getOriginalFilename();
            File destinationFile = new File(filePath);

            // 파일 저장
            image.transferTo(destinationFile);

            // 성공 시 응답 데이터 설정
            responseDTO.setResponse("Image uploaded successfully to " + filePath);
        } catch (IOException e) {
            // 오류 발생 시 응답 데이터 설정
            responseDTO.setStatus("error");
            responseDTO.setError("Image upload failed: " + e.getMessage());
        }

        return responseDTO;  // 응답 객체 반환
    }
}
