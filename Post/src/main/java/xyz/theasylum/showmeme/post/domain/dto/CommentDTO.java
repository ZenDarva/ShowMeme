package xyz.theasylum.showmeme.post.domain.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String postId;
    private String parentCommentId;
    private String body;
}
