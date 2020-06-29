package xyz.theasylum.showmeme.post.kafka.domain;

import lombok.Data;
import xyz.theasylum.showmeme.post.domain.Post;

@Data
public class KafkaViewPost extends KafkaPost {
    String id;

    public KafkaViewPost(Post targPost){
        id = targPost.getId();
    }
    public KafkaViewPost(){

    }
}
