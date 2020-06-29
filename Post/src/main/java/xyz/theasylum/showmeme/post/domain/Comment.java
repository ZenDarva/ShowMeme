package xyz.theasylum.showmeme.post.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class Comment {
    @Id
    private String id;

    private String postId;
    private String parentId;
    private int votes =0;
    private String postedBy;
    private String body;

    @Transient
    private List<Comment> children;
}
