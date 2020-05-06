package xyz.theasylum.showmeme.post.domain.dto;

import lombok.Data;

@Data
public class ImageDTO {

    private String contentType;
    private byte[] data;
    private String hash;

}
