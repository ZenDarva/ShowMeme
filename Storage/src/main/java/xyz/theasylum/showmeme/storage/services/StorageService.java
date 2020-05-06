package xyz.theasylum.showmeme.storage.services;

import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.theasylum.showmeme.common.util.Base62;
import xyz.theasylum.showmeme.storage.domain.Image;
import xyz.theasylum.showmeme.storage.domain.dto.ImageDTO;
import xyz.theasylum.showmeme.storage.repositories.ImageRepository;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/files")
public class StorageService {

    private static final int SIZE_LIMIT = 12000000;
    private ImageRepository repository;


    public StorageService(ImageRepository repository) throws NoSuchAlgorithmException {
        this.repository = repository;
    }

    @PostMapping("/{filename:.+\\.\\w\\w\\w}")
    public ResponseEntity<String> storeImage(@RequestParam("File") MultipartFile file, @PathVariable String filename) {

        if (file.getSize() > SIZE_LIMIT) {
            return new ResponseEntity<>("Attachment too large", HttpStatus.PAYLOAD_TOO_LARGE);
        }
        Image img = new Image();
        img.setFilename(filename);
        try {
            Binary bin = new Binary(file.getBytes());
            img.setImageData(bin);
        } catch (IOException e) {
            return new ResponseEntity<>("Error saving file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        img.setHash(Base62.getHash(img.getImageData().getData()));
        Image dupImage = repository.findByHash(img.getHash());
        if (dupImage == null) {
            repository.save(img);
            return new ResponseEntity<String>(img.getHash(), HttpStatus.OK);
        }

        return new ResponseEntity<String>(dupImage.getHash(), HttpStatus.OK);
    }

    @GetMapping("/hash/{hash:.+}")
    public ResponseEntity getImageByHash(@PathVariable String hash) {
        Image img = repository.findByHash(hash);
        if (img == null || !img.isViewable()) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        ImageDTO dto = new ImageDTO(img);
        if (dto.getContentType() == "unknown"){
            return new ResponseEntity("File damaged.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(new ImageDTO(img), HttpStatus.OK);
    }

    @PatchMapping("/visible/{hash:.+}")
    public ResponseEntity<String> setVisible(@PathVariable String hash, @RequestBody boolean visible){
        Image img = repository.findByHash(hash);
        if (img == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        img.setViewable(visible);
        repository.save(img);
        return new ResponseEntity<>("",HttpStatus.OK);
    }


}
