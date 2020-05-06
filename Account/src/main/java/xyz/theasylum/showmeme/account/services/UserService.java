package xyz.theasylum.showmeme.account.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserService {



    @GetMapping(value = "/all", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity checkAll(){
        return new ResponseEntity<>("Content!", HttpStatus.OK);
    }


    @GetMapping(value = "/admin", produces = MediaType.TEXT_HTML_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity checkAdmin(){
        return new ResponseEntity("Admin Content!",HttpStatus.OK);
    }

    @GetMapping(value = "/user", produces = MediaType.TEXT_HTML_VALUE)
    @Secured("ROLE_USER")
    public ResponseEntity checkUser2(){
        return new ResponseEntity("User Content!",HttpStatus.OK);
    }

    @GetMapping(value = "/mod", produces = MediaType.TEXT_HTML_VALUE)
    @Secured("ROLE_MOD")
    public ResponseEntity checkMod(){
        return new ResponseEntity("Mod Content!",HttpStatus.OK);
    }
}
