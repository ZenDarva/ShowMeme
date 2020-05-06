package xyz.theasylum.showmeme.post.services;

import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.theasylum.showmeme.post.ImageCache;
import xyz.theasylum.showmeme.post.domain.CachedImage;
import xyz.theasylum.showmeme.post.domain.Post;
import xyz.theasylum.showmeme.post.domain.dto.NewPostDTO;
import xyz.theasylum.showmeme.post.repositories.PostRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/post")
public class PostService {


    @Autowired
    ImageCache imageCache;

    @Autowired
    PostRepository postRepository;

    @PostMapping(value = "/createPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createPost(@RequestParam("postDTO") NewPostDTO newPostDTO, @RequestParam("File") MultipartFile[] files) throws IOException {
        List<String> hashes = new ArrayList<>(files.length);
        Post post = new Post(files.length);
        for (int i = 0; i < files.length; i++) {
            String hash = imageCache.addNewImageToCache(files[i].getOriginalFilename(),files[i].getBytes());
            post.getImageHashes().add(i,hash);
            post.getAltTexts().add(i, newPostDTO.getAltTexts()[i]);
        }
        post.setTitle(newPostDTO.getTitle());
        post.setDescription(newPostDTO.getDescription());
        post.setPosted(Instant.now().getMillis());
        postRepository.save(post);
        return new ResponseEntity(post.getImageHashes().get(0), HttpStatus.OK);
    }

    @GetMapping(value="/image/{hash:.+}")
    public ResponseEntity getImage(@PathVariable("hash") String hash){
        CachedImage image = imageCache.getImage(hash);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getContentType())).body(image.getImageData().getData());
    }

    @GetMapping(value="/getNewest")
    public ResponseEntity getNewest(){
        List<Post> posts= postRepository.findFirst50ByOrderByPostedDesc();
        return new ResponseEntity(posts, HttpStatus.OK);

    }

    @GetMapping("/test")
    public ResponseEntity test(){
        List<Post> posts= postRepository.findFirst50ByOrderByPostedDesc();
        return new ResponseEntity(posts, HttpStatus.OK);
    }

}
