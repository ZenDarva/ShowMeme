package xyz.theasylum.showmeme.post.repositories;

import org.joda.time.Instant;
import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.theasylum.showmeme.post.domain.Post;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    public List<Post> findAllByPostedAfter(Instant time);
    public List<Post> findFirst50ByOrderByPostedDesc();
}
