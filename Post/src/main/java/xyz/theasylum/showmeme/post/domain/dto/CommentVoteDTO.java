package xyz.theasylum.showmeme.post.domain.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CommentVoteDTO {
  private String commentId;

  @Min(-1)
  @Max(+1)
  private int vote;

}
