package xyz.theasylum.showmeme.post.kafka.domain;

import xyz.theasylum.showmeme.post.domain.Comment;

public abstract class KafkaComment {

    private String id;

    private String postId;
    private String parentId;
    private int votes =0;
    private String postedBy;
    private String body;

    public KafkaComment(Comment comment){
        this.id = comment.getId();
        this.body=comment.getBody();
        this.postId=comment.getPostId();
        this.postedBy=comment.getPostedBy();
        this.votes=comment.getVotes();
        this.parentId=comment.getParentId();
    }

    public KafkaComment(){};

    public Comment toComment(){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setBody(body);
        comment.setPostId(postId);
        comment.setPostedBy(postedBy);
        comment.setVotes(votes);
        comment.setParentId(parentId);
        return comment;
    }
}
