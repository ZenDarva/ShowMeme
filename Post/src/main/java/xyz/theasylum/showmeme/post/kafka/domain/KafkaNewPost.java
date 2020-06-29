package xyz.theasylum.showmeme.post.kafka.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.theasylum.showmeme.post.domain.Post;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class KafkaNewPost extends KafkaPost {
    String id;

    private String title;
    private String description;
    private List<String> altTexts;
    private List<String> imageHashes;
    private String posterUsername;
    private boolean publicPost = true;
    private boolean visiblePost = true;
    private Long posted;
    private Long views;

    public KafkaNewPost(Post fromPost){
        this.id=fromPost.getId();
        this.title=fromPost.getTitle();
        this.description=fromPost.getDescription();
        this.altTexts=List.copyOf(fromPost.getAltTexts());
        this.imageHashes=List.copyOf(fromPost.getImageHashes());
        this.posterUsername=fromPost.getPosterUsername();
        this.publicPost=fromPost.isPublicPost();
        this.visiblePost=fromPost.isVisiblePost();
        this.posted=fromPost.getPosted();
        this.views=fromPost.getViews();
    }
    public KafkaNewPost(){};

    public Post toPost(){
        Post post = new Post(altTexts.size());
        post.setId(id);
        post.setTitle(title);
        post.setDescription(description);
        post.setAltTexts(List.copyOf(altTexts));
        post.setImageHashes(List.copyOf(imageHashes));
        post.setPosterUsername(posterUsername);
        post.setPublicPost(publicPost);
        post.setVisiblePost(visiblePost);
        post.setPosted(posted);
        post.setViews(views);
        return post;
    }
}
