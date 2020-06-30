package xyz.theasylum.showmeme.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.LinkedList;
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
    @JsonIgnore
    private List<String> votedUp = new LinkedList<>();
    @JsonIgnore
    private List<String> votedDown = new LinkedList<>();

    public void adjustVote(int amount){
        votes+=amount;
    }

    @Transient
    private List<Comment> children = new LinkedList<>();
    @Transient
    private int userVote=0;
}
