package xyz.theasylum.showmeme.post;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import xyz.theasylum.showmeme.common.util.Base62;
import xyz.theasylum.showmeme.post.clients.StorageClient;
import xyz.theasylum.showmeme.post.domain.CachedImage;
import xyz.theasylum.showmeme.post.domain.dto.ImageDTO;
import xyz.theasylum.showmeme.post.repositories.CachedImageRepository;

import java.security.NoSuchAlgorithmException;

@Service
@Scope("singleton")
public class ImageCache {
    private CachedImageRepository cachedImageRepository;
    private StorageClient storageClient;

    public ImageCache(CachedImageRepository cachedImageRepository, StorageClient storageClient) throws NoSuchAlgorithmException {
        this.cachedImageRepository = cachedImageRepository;
        this.storageClient = storageClient;
    }

    public String addNewImageToCache(String filename, byte[] bytes){
        String hash = Base62.getHash(bytes);
        if (checkExists(hash))
            return hash;
        //It doesn't exist locally.  Send it to the storage service.  If it exists there, it'll be discarded
        //and the appropriate hash returned.
        //May refactor to avoid unnecessary uploads.
        hash = storageClient.uploadImage(filename,bytes);
        if (hash == null){
            //upload to storage failed! All we can do is bail.
            return null;
        }
        CachedImage newCachedImage =new CachedImage(hash,bytes,filename);
        cachedImageRepository.save(newCachedImage);
        return hash;
    }

    public CachedImage getImage(String hash){
        if (checkExists(hash))
            return cachedImageRepository.getByHash(hash);
        ImageDTO dto = storageClient.getImage(hash);
        CachedImage image = new CachedImage(dto);
        cachedImageRepository.save(image);
        return image;
    }

    private boolean checkExists(String hash){
        return (cachedImageRepository.getByHash(hash) != null);
    }


}
