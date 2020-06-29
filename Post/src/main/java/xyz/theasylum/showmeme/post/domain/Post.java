package xyz.theasylum.showmeme.post.domain;

import lombok.Data;
import org.joda.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("posts")
public class Post {

    @Id
    String id;

    private String title;
    private String description;
    private List<String> altTexts;
    private List<String> imageHashes;
    private String posterUsername;
    private boolean publicPost = true;
    private boolean visiblePost = true;
    private Long posted;
    private long views;

    @Transient
    private List<Comment> comments;

    public Post(int size) {
        altTexts= new ArrayList<>(size);
        imageHashes= new ArrayList<>(size);
    }

    public Post(){}
}
