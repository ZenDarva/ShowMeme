package xyz.theasylum.showmeme.post.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.theasylum.showmeme.post.domain.Comment;
import xyz.theasylum.showmeme.post.domain.Post;
import xyz.theasylum.showmeme.post.domain.dto.CommentVoteDTO;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaCommentVote;
import xyz.theasylum.showmeme.post.repositories.CommentRepository;
import xyz.theasylum.showmeme.post.repositories.PostRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static xyz.theasylum.showmeme.post.utility.CommentUtil.getCommentsChildren;
import static xyz.theasylum.showmeme.post.utility.CommentUtil.organizeComments;


@RequestMapping("/api/comment")
@RestController()
public class CommentService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

//    @Autowired
//    KafkaCommentTopic commentTopic;

    @PostMapping(value="/vote")
    private ResponseEntity voteComment(@RequestBody CommentVoteDTO voteDTO, Principal principal){
        Optional<Comment> oComment = commentRepository.findById(voteDTO.getCommentId());

        if (oComment.isEmpty()){
            return new ResponseEntity("That comment does not exist.", HttpStatus.BAD_REQUEST);
        }
        Comment comment = oComment.get();

        switch (voteDTO.getVote()){
            case 1:
                if (comment.getVotedUp().contains(principal.getName())){
                    //we're removing our upvote.
                    comment.getVotedUp().remove(principal.getName());
                    comment.adjustVote(-1);
                }
                else if (comment.getVotedDown().contains(principal.getName())){
                    //We downvoted, and we're switching to a upvote.
                    comment.getVotedDown().remove(principal.getName());
                    comment.getVotedUp().add(principal.getName());
                    comment.adjustVote(2);
                }
                else {
                    //We're upvoting.
                    comment.getVotedUp().add(principal.getName());
                    comment.adjustVote(1);
                }
                break;
            case -1:
                if (comment.getVotedDown().contains(principal.getName())){
                    //we're removing our downvote.
                    comment.getVotedDown().remove(principal.getName());
                    comment.adjustVote(+1);
                }
                else if (comment.getVotedUp().contains(principal.getName())){
                    //We upvoted, and we're switching to a downvote.
                    comment.getVotedUp().remove(principal.getName());
                    comment.getVotedDown().add(principal.getName());
                    comment.adjustVote(-2);
                }
                else {
                    //We're downvoting.
                    comment.getVotedDown().add(principal.getName());
                    comment.adjustVote(-1);
                }
                break;
            case 0:
                //We're an asshole.  Fuck off.
        }

        KafkaCommentVote kVote = KafkaCommentVote.fromCommentVoteDTO(voteDTO, principal.getName());
        //commentTopic.sendPostMessage(kVote);
        commentRepository.save(comment);
        return new ResponseEntity(null,HttpStatus.OK);
    };

    @GetMapping(value="/{postHash:.+}/{commentHash:.+")
    public ResponseEntity getComment(@PathVariable("commentHash") String commentId, @PathVariable("postHash") String postId, Principal principal){
        Optional<Comment> oComment = commentRepository.findById(commentId);
        if (oComment.isEmpty()){
            return new ResponseEntity("No such comment",HttpStatus.BAD_REQUEST);
        }
        Comment comment = oComment.get();
        //TODO: Refactor to use postId
        List<Comment> comments = commentRepository.getAllByPostId(comment.getPostId());
        comments.stream().filter(f->f.getVotedUp().contains(principal.getName())).forEach(f->f.setUserVote(1));
        comments.stream().filter(f->f.getVotedDown().contains(principal.getName())).forEach(f->f.setUserVote(-1));

        if (comment.getVotedUp().contains(principal.getName())){
            comment.setUserVote(1);
        }
        if (comment.getVotedDown().contains(principal.getName())){
            comment.setUserVote(-1);
        }

        comment = getCommentsChildren(comments,comment);

        return new ResponseEntity(comment,HttpStatus.OK);
    }

    @GetMapping(value="/{postHash:.+}")
    public ResponseEntity getComments(@PathVariable("postHash") String postHash, Principal principal){
        List<Comment> comments = commentRepository.getAllByPostId(postHash);
        comments.stream().filter(f->f.getVotedUp().contains(principal.getName())).forEach(f->f.setUserVote(1));
        comments.stream().filter(f->f.getVotedDown().contains(principal.getName())).forEach(f->f.setUserVote(-1));



        return new ResponseEntity(organizeComments(comments),HttpStatus.OK);
    }
}
