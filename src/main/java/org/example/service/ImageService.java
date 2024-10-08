package org.example.service;

import org.example.dto.ImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    /**
     * 사용자 이미지 업로드 로직 정의
     * @param userId 사용자 ID
     * @param image 업로드할 이미지 파일
     * @return 업로드 결과를 포함한 응답 DTO
     */
    ImageResponseDTO uploadImage(Long userId, MultipartFile image);
}

