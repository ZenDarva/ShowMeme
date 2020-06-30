package xyz.theasylum.showmeme.post.kafka.domain;

import lombok.Data;
import xyz.theasylum.showmeme.post.domain.dto.CommentVoteDTO;

@Data
public class KafkaCommentVote extends KafkaComment {
    private String commentId;
    private int amount;
    private String byUser;

    public static KafkaCommentVote fromCommentVoteDTO(CommentVoteDTO dto, String user){
        KafkaCommentVote kVote = new KafkaCommentVote();
        kVote.commentId=dto.getCommentId();
        kVote.amount=dto.getVote();
        return kVote;
    }


}
