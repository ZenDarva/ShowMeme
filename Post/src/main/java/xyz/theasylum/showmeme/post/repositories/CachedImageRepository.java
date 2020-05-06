package xyz.theasylum.showmeme.post.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.theasylum.showmeme.post.domain.CachedImage;

public interface CachedImageRepository extends MongoRepository<CachedImage, String> {

    public CachedImage getByHash(String hash);
}
