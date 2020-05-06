package xyz.theasylum.showmeme.post.domain.dto;


import lombok.Data;

@Data
public class NewPostDTO {
    private String title;
    private String description;
    private String[] altTexts;
}
