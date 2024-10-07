/**
package org.example.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 사용자 세부 정보 수정 로직 정의
     * @param userId 사용자 ID
     * @param image 이미지 파일
     * @param requestDTO 사용자 세부 정보 DTO
     * @return 수정 결과를 포함한 응답 DTO
     */

    /**
     * 사용자 이미지 삭제 로직 정의
     * @param userId 사용자 ID
     * @return 삭제 결과를 포함한 응답 DTO


    UserDetailResponseDTO updateUserDetails(Long userId, MultipartFile image, UserDetailRequestDTO requestDTO);
    UserDetailResponseDTO deleteUserImage(Long userId);
}
/*
