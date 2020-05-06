package xyz.theasylum.showmeme.storage.domain;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="images")
@Data
public class Image {
    @Id
    private String id;

    private String hash;

    private Binary imageData;

    private String filename;

    private boolean isViewable;

    public Image(){
        isViewable=true;
    }

}
