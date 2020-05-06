package xyz.theasylum.showmeme.post.domain;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import xyz.theasylum.showmeme.post.domain.dto.ImageDTO;

import java.util.Date;

@Document("cachedimage")
@Data
public class CachedImage {
    @Id
    private String id;

    private String hash;

    private Binary imageData;

    private String contentType;

    @Indexed(name="timeCached", expireAfter = "1d")
    private Date timeCached;


    public CachedImage(ImageDTO dto){
        hash =dto.getHash();
        imageData = new Binary(dto.getData());
        contentType=dto.getContentType();
        timeCached=new Date();
    }

    public CachedImage(String hash, byte[] data, String filename){
        this.hash=hash;
        imageData = new Binary(data);
        timeCached = new Date();
        switch (filename.substring(filename.length() - 3).toLowerCase()) {
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
    public CachedImage(){}
}
