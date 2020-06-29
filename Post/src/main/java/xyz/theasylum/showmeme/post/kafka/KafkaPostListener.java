package xyz.theasylum.showmeme.post.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import xyz.theasylum.showmeme.post.PostApplication;
import xyz.theasylum.showmeme.post.domain.Post;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaNewPost;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaPost;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaViewPost;
import xyz.theasylum.showmeme.post.repositories.PostRepository;

import java.util.Optional;

//@Service
public class KafkaPostListener {
    @Autowired
    PostRepository postRepository;

    @Value("${application.id}")
    String id;

    @KafkaListener(topics = "posts",groupId="${application.id}")
    public void receive(@Payload KafkaPost message,
                        @Headers MessageHeaders headers) {

        if (headers.get("producer").equals(id)){
            //We produced this message, we shouldn't process it too.
            return;
        }

        if (message instanceof KafkaNewPost){
            addPost((KafkaNewPost) message);
            return;
        }

        if (message instanceof KafkaViewPost){
            viewPostIncrement((KafkaViewPost) message);
        }

    }

    private void viewPostIncrement(KafkaViewPost message) {
        Optional<Post> localPost = postRepository.findById(message.getId());
        if (localPost.isEmpty()){
            //Well, this is a problem.  We can't increment the views of a post we don't have.  Blah.
            return;
        }
        Post post = localPost.get();
        post.setViews(post.getViews()+1);
        postRepository.save(post);
    }

    private void addPost(KafkaNewPost message) {
        Optional<Post> localPost = postRepository.findById(message.getId());
        if (localPost.isEmpty()){
            //We don't have this one.  We need to add it.
            postRepository.save(message.toPost());
        }
    }
}
