package xyz.theasylum.showmeme.post.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.theasylum.showmeme.post.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    public List<Comment> getAllByPostId(String id);
}
