package xyz.theasylum.showmeme.storage.domain.dto;

import lombok.Data;
import xyz.theasylum.showmeme.storage.domain.Image;

@Data
public class ImageDTO {

    private String contentType;
    private byte[] data;
    private String hash;

    public ImageDTO(Image image) {
        data = image.getImageData().getData();
        hash = image.getHash();
        switch (image.getFilename().substring(image.getFilename().length() - 3).toLowerCase()) {
            case "png":
                contentType = "image/png";
                break;
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "mp4":
                contentType = "video/mpeg";
            default:
                contentType = "unknown";
        }
    }
}
