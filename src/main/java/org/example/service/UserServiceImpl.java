/**
package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final String uploadDir = "C:/uploads";  // 파일 저장 기본 경로 설정

    @Override
    public UserDetailResponseDTO updateUserDetails(Long userId, MultipartFile image, UserDetailRequestDTO requestDTO) {
        UserDetailResponseDTO responseDTO = new UserDetailResponseDTO();
        responseDTO.setStatus("success");

        try {
            // 사용자별 폴더 생성
            String directoryPath = uploadDir + "/user_" + userId;
            Files.createDirectories(Paths.get(directoryPath));  // 경로가 없을 경우 디렉토리를 생성

            // 파일명 중복 방지를 위해 UUID를 사용하여 고유한 파일명 생성
            String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String filePath = directoryPath + "/" + uniqueFileName;
            File destinationFile = new File(filePath);

            // 이미지 저장
            image.transferTo(destinationFile);

            // 성공 시 응답 데이터 설정
            responseDTO.setResponse("Details updated successfully with image at " + filePath);
        } catch (IOException e) {
            // 파일 저장 시 발생하는 예외 처리
            responseDTO.setStatus("error");
            responseDTO.setError("Failed to save image file: " + e.getMessage());
            e.printStackTrace();  // 콘솔에 예외 메시지 출력
        } catch (Exception e) {
            // 기타 예외 처리
            responseDTO.setStatus("error");
            responseDTO.setError("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();  // 콘솔에 예외 메시지 출력
        }

        return responseDTO;  // 응답 DTO 반환
    }
    @Override
    public UserDetailResponseDTO deleteUserImage(Long userId) {
        UserDetailResponseDTO responseDTO = new UserDetailResponseDTO();
        responseDTO.setStatus("success");

        // 사용자 이미지 파일 경로
        String userDirectoryPath = uploadDir + "/user_" + userId;

        try {
            // 해당 사용자의 디렉토리 및 파일을 확인
            File userDirectory = new File(userDirectoryPath);

            if (userDirectory.exists() && userDirectory.isDirectory()) {
                // 사용자 폴더 내부 파일 삭제
                for (File file : userDirectory.listFiles()) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
                responseDTO.setResponse("User image deleted successfully.");
            } else {
                responseDTO.setStatus("error");
                responseDTO.setError("User image directory does not exist.");
            }
        } catch (Exception e) {
            responseDTO.setStatus("error");
            responseDTO.setError("Failed to delete user image: " + e.getMessage());
            e.printStackTrace();  // 콘솔에 예외 메시지 출력
        }

        return responseDTO;
    }
*/