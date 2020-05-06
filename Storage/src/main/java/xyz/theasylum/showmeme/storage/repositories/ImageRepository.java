package xyz.theasylum.showmeme.storage.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.theasylum.showmeme.storage.domain.Image;

public interface ImageRepository extends MongoRepository<Image, String> {
    Image findByHash(String hash);
    Image findByFilename(String filename);
}
