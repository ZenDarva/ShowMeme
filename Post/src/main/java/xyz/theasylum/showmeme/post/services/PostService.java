package xyz.theasylum.showmeme.post.services;

import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.theasylum.showmeme.post.ImageCache;
import xyz.theasylum.showmeme.post.domain.CachedImage;
import xyz.theasylum.showmeme.post.domain.Comment;
import xyz.theasylum.showmeme.post.domain.Post;
import xyz.theasylum.showmeme.post.domain.dto.CommentDTO;
import xyz.theasylum.showmeme.post.domain.dto.NewPostDTO;
import xyz.theasylum.showmeme.post.kafka.KafkaPostTopic;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaNewPost;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaViewPost;
import xyz.theasylum.showmeme.post.repositories.CommentRepository;
import xyz.theasylum.showmeme.post.repositories.PostRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController()
@RequestMapping("/api/post")
public class PostService {


    @Autowired
    ImageCache imageCache;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;


    @Autowired
    KafkaPostTopic postTopic;


    @PostMapping(value = "/createPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createPost(@RequestParam("postDTO") NewPostDTO newPostDTO, @RequestParam("File") MultipartFile[] files, Principal principal ) throws IOException {
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
        post.setPosterUsername(principal.getName());
        postRepository.save(post);
        postTopic.sendPostMessage(new KafkaNewPost(post));
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

    @GetMapping(value="/post/{hash:.+}")
    public ResponseEntity getPost(@PathVariable("hash") String postHash){
        Optional<Post> post = postRepository.findById(postHash);
        if (post.isEmpty()){
            return new ResponseEntity("Not Found", HttpStatus.NOT_FOUND);
        }
        post.get().setViews(post.get().getViews()+1);
        postRepository.save(post.get());
        postTopic.sendPostMessage(new KafkaViewPost(post.get()));

        List<Comment> comments = commentRepository.getAllByPostId(postHash);
        post.get().setComments(organizeComments(comments));
        return new ResponseEntity(post.get(),HttpStatus.OK);

    }
    private List<Comment> organizeComments(List<Comment> baseComments){
        List<Comment> unsortedComments = new LinkedList<>();
        List<Comment> sortedComments = new LinkedList<>();

        unsortedComments.addAll(baseComments);

        baseComments.stream().filter(f->f.getParentId()==null).forEach(sortedComments::add);
        baseComments.removeAll(sortedComments);

        List<Comment> toRemove = new LinkedList<>();
        while (!baseComments.isEmpty()){
            for (Comment baseComment : baseComments) {
                for (Comment sortedComment : unsortedComments) {
                    if (sortedComment.getId().equals(baseComment.getParentId())){
                        if (sortedComment.getChildren()==null){
                            sortedComment.setChildren(new LinkedList<>());
                        }
                        sortedComment.getChildren().add(baseComment);
                        toRemove.add(baseComment);
                    }
                }
            }
            baseComments.removeAll(toRemove);
            toRemove.clear();
        }
        return sortedComments;

    }

    @PostMapping("/addComment")
    @Secured("ROLE_USER")
    public ResponseEntity addComment(@RequestBody CommentDTO comment, Principal principal){

        Optional<Post> post = postRepository.findById(comment.getPostId());
        //Need update here to prevent posting comments on deleted posts.
        if (post.isEmpty()){
            return new ResponseEntity("No such post exists.", HttpStatus.BAD_REQUEST);
        }
        if (comment.getParentCommentId()!=null) {
            Optional<Comment> parentComment = commentRepository.findById(comment.getParentCommentId());
            if (parentComment.isEmpty() || !parentComment.get().getPostId().equals(comment.getPostId()))
                return new ResponseEntity("Bad request", HttpStatus.BAD_REQUEST);
        }
        Comment newComment = new Comment();
        newComment.setBody(comment.getBody());
        newComment.setPostId(comment.getPostId());
        newComment.setPostedBy(principal.getName());
        newComment.setParentId(comment.getParentCommentId());

        commentRepository.save(newComment);

        return new ResponseEntity("",HttpStatus.OK);
    }



    @GetMapping("/test")
    public ResponseEntity test(){
        List<Post> posts= postRepository.findFirst50ByOrderByPostedDesc();
        return new ResponseEntity(posts, HttpStatus.OK);
    }



}
