
package org.example.domain.adminpage.AdminController;

import lombok.RequiredArgsConstructor;
import org.example.domain.adminpage.AdminService.AdminpageService;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.*;
import org.example.domain.user.UserDTO.UserResponseDTO;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.example.global.response.ResponseEntityProvider;
import org.example.domain.user.UserEntity.State;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserRepository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AdminpageController {
    private final AdminpageService adminpageService;
    private final ResponseEntityProvider responseEntityProvider;
    private final UserRepository userRepository;

    @GetMapping("/admin/state")
    public ResponseEntity AdminPage() {
        List<User> waitingUsers = adminpageService.getPendingUsers();
        List<User> approvedUsers = adminpageService.getapprovedUsers();
        List<UserResponseDTO.adminWaitingResponseDTO> adminWaitingResponseDTOList = waitingUsers.stream()
                .map(user -> new UserResponseDTO.adminWaitingResponseDTO(user.getEmail(), user.getName(), user.getCreatedAt()))
                .collect(Collectors.toList()); // 리스트로 수집
        List<UserResponseDTO.adminmemberResponseDTO> adminmemberResponseDTOList = approvedUsers.stream()
                .map(user -> new UserResponseDTO.adminmemberResponseDTO(user.getEmail(), user.getName(), user.getCardinal(), user.getPart()))
                .collect(Collectors.toList()); // 리스트로 수집
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("PendingList", adminWaitingResponseDTOList);
        responseMap.put("ApprovedList", adminmemberResponseDTOList);

        // Return response with data
        return responseEntityProvider.successWithData("성공적으로 데이터를 불러왔습니다.", responseMap);
    }

    @PostMapping("/admin/user/approval/{user_id}")
    public ResponseEntity AdminApproval(@PathVariable("user_id") Long userId) {
        User user = userRepository.findById(userId);
        user.setState(State.approved);
        userRepository.save(user);
        return responseEntityProvider.successWithoutData("승인하였습니다.");
    }

    @PostMapping("/admin/user/rejection/{user_id}")
    public ResponseEntity Adminrejection(@PathVariable("user_id") Long userId) {
        User user = userRepository.findById(userId);
        user.setState(State.rejected);
        userRepository.save(user);
        return responseEntityProvider.successWithoutData("거절하였습니다.");
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity deleteMember(@PathVariable("user_id") Long userId) {
        User user = userRepository.findById(userId);
        userRepository.delete(user);
        return responseEntityProvider.successWithoutData("멤버를 삭제하였습니다.");
    }

    //관리자 게시글 리스트 조회
    @GetMapping("/admin/post/{index}")
    public ResponseEntity getAdminPostList(@PathVariable("index") int index) {
        List<PostResponseDTO.AdminPostResponseDTO> adminPostResponseDTOList = (List<PostResponseDTO.AdminPostResponseDTO>) adminpageService.AdminPaging(index);


        return responseEntityProvider.successWithData("리스트 불러오기에 성공하였습니다.", adminPostResponseDTOList);


    }
}





