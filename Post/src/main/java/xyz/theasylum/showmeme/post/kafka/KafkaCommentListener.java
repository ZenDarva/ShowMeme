package xyz.theasylum.showmeme.post.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaComment;
import xyz.theasylum.showmeme.post.repositories.PostRepository;

public class KafkaCommentListener {
    @Autowired
    PostRepository postRepository;

    @Value("${application.id}")
    String id;

    @KafkaListener(topics = "comments",groupId="${application.id}")
    public void Receive(@Payload KafkaComment message,
                        @Headers MessageHeaders headers){

    }
}
