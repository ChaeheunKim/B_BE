package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostDetailResponseDTO;
import org.example.dto.PostRequestDTO;
import org.example.dto.PostResponseDTO;
import org.example.entity.Category;
import org.example.entity.Post;
import org.example.entity.PostImage;
import org.example.entity.User;
import org.example.repository.PostImageRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    private final PostImageService postImageService;  // Spring이 관리하는 PostImageService 주입

    /**
     * Post 등록
     * @param requestDTO - 게시물 요청 데이터
     * @param images - 업로드할 이미지 파일 리스트
     * @return postId - 생성된 게시물의 ID
     */
    public boolean createPost(PostRequestDTO requestDTO, List<MultipartFile> images){

        try {
            Post post = requestDTO.toEntity(requestDTO);
            boolean imgThumbnail = requestDTO.isImgThumbnail();
            post = postRepository.save(post);


            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            postImageService.uploadPostImages(post, images, imgThumbnail);

            return true;
        } catch (Exception e) {
            // 예외 처리 로직 (로깅 등)
            return false; // 어떤 이유로든 실패하면 false 반환
        }

    }

    public PostResponseDTO getPostList(Category category) {
        List<Post> posts = postRepository.findByCategory(category);
        List<PostImage> allPostImages = new ArrayList<>();

        // 각 Post에 대해 PostImage를 찾아서 리스트에 추가
        for (Post post : posts) {
            List<PostImage> postImages = postImageRepository.findByPost_postIdAndImgThumbnailTrue(post.getPostId());
            allPostImages.addAll(postImages);
        }

        // Post와 PostImage를 매칭하여 PostItem으로 변환
        List<PostResponseDTO.PostItem> postItems = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            String imageUrl = i < allPostImages.size() ? allPostImages.get(i).getImgUrl() : null;
            postItems.add(convertToPostItem(post, imageUrl));
        }

        PostResponseDTO responseDTO = new PostResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setError(null);

        PostResponseDTO.Response response = new PostResponseDTO.Response();
        response.setPost_list(postItems);

        responseDTO.setResponse(response);

        return responseDTO;
    }

    public PostDetailResponseDTO getdetailImage(int postId, Post post){
        List<PostImage> images = postImageRepository.findByPost_postId(postId);

        // 추가 - imgUrl을 List<String>에 채우기
        List<String> imageUrls = images.stream()
                .map(PostImage::getImgUrl) // PostImage에서 imgUrl을 가져옴
                .collect(Collectors.toList()); // List<String>으로 수집
        // 3. 이 두가지를 묶은 PostResponseDTO를 최종적으로 return해줌.
        String participantsString = post.getParticipant();
        List<PostDetailResponseDTO.DetailPostItem.Participant> participantList = Arrays.stream(participantsString.split(","))
                .map(name -> {
                    PostDetailResponseDTO.DetailPostItem.Participant participantItem = new PostDetailResponseDTO.DetailPostItem.Participant();
                    participantItem.setName(name.trim());
                    return participantItem;
                })
                .collect(Collectors.toList());

        // 4. PostResponseDTO 구성
        PostDetailResponseDTO responseDTO = new PostDetailResponseDTO();
        responseDTO.setStatus("success");

        PostDetailResponseDTO.DetailResponse response = new PostDetailResponseDTO.DetailResponse();
        List<PostDetailResponseDTO.DetailPostItem> postItems = new ArrayList<>();

        PostDetailResponseDTO.DetailPostItem DetailPostItem = new PostDetailResponseDTO.DetailPostItem();
        DetailPostItem.setTitle(post.getTitle()); // 포스트 제목
        DetailPostItem.setImage(imageUrls); // 이미지 URL 리스트
        DetailPostItem.setContent(post.getContent());
        DetailPostItem.setPeriod(post.getPeriod()); // 기간
        DetailPostItem.setParticipant(participantList); // 참여자 리스트

        // postItem을 postItems에 추가
        postItems.add(DetailPostItem);
        response.setDetail_list(postItems); // postItems를 response에 설정

        responseDTO.setResponse(response); // response를 DTO에 설정

        return responseDTO;
    }

    private PostResponseDTO.PostItem convertToPostItem(Post post, String imageUrl) {
        PostResponseDTO.PostItem item = new PostResponseDTO.PostItem();
        item.setPost_id(post.getPostId());
        item.setPost_title(post.getTitle());
        item.setImage(imageUrl);
        item.setProject_category(post.getProjectCategory().toString());
        item.setPeriod(post.getPeriod()); // LocalDateTime을 String으로 변환. 필요시 형식 지정 가능
        item.setPart(post.getParticipant()); // 'part' 필드에 'participant' 값을 설정. 필요에 따라 수정 가능 -> 스터디에만 파트가 있으므로 구조가 잘못됐다! front에서 잘 빼서 쓰면 되긴 하지만 문제인 부분이다.

        return item;
    }

    // postDetail을 post에 담아 가져오기
    public Post getPostDetail(int postId) {
        Optional<Post> post = postRepository.findById(postId);

        // 포스트가 없으면 null을 반환
        return post.orElseThrow(() -> new IllegalArgumentException("게시글 상세조회 실패! 해당 게시글이 없습니다.")); // 또는 return post.orElse(null);
    }
    
    // 게시글 상세조회에 넣을 이미지들을 불러오는 메소드
    public List<PostImage> getDetailImages(int postId){

        return postImageRepository.findByPost_postId(postId);
    }

    public int deletePost(int post_id){
        // 게시글 조회 및 예외 처리

        Post target = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제 실패! 해당 게시글이 없습니다."));

        // 게시글 삭제
        postRepository.delete(target);

        // 게시글 번호 반환
        return target.getPostId();
    }

//    public int updatePost(PostRequestDTO requestDTO){
//        // 게시글 조회 및 예외 처리
//
//            Post target = postRepository.findById(requestDTO.getPost_id())
//                    .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패! 해당 게시글이 없습니다."));
//
//            // 게시글 수정
//            target.builder()
//                    .title(requestDTO.getTitle())
//                    .content(requestDTO.getContent())
//                    .category(requestDTO.getCategory())
//                    .participant(requestDTO.getParticipant())
//                    .projectCategory(requestDTO.getProjectCategory())
//                    .period(requestDTO.getPeriod())
//                    .build();
//
//            postRepository.save(target);
//
//            // 게시글 번호 반환
//            return target.getPostId();
//    }
//    private PostResponseDTO convertToDTO(Post post){
//        // Entity를 DTO로 변환
//        PostResponseDTO dto = new PostResponseDTO();
//
//        return dto;
//    }


}
