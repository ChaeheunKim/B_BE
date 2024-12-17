package org.example.domain.post.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.post.Entity.ProjectCategory;
import org.example.domain.user.UserEntity.Part;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDetailResponseDTO {

        private String title;
        private String content;
        private LocalDateTime period;
        private List<String> imageUrl;
        private List<String> participant;
        private Part part;
        private ProjectCategory projectCategory;

        public PostDetailResponseDTO(String title,String content, LocalDateTime period,List<String> imageUrl, List<String> participant,Part part,ProjectCategory projectCategory){
                this.title=title;
                this.content=content;
                this.period=period;
                this.imageUrl=imageUrl;
                this.participant=participant;
                this.part=part;
                this.projectCategory=projectCategory;
        }



}
