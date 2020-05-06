package xyz.theasylum.showmeme.account.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import xyz.theasylum.showmeme.account.domain.*;
import xyz.theasylum.showmeme.account.domain.dto.LoginConfirmDto;
import xyz.theasylum.showmeme.account.domain.dto.LoginDto;
import xyz.theasylum.showmeme.account.domain.dto.UserDto;
import xyz.theasylum.showmeme.account.repositories.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthService(@Autowired UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDto){
        UserData user = new UserData(userDto,passwordEncoder);
        if (userRepository.findFirstByEmail(user.getEmail()) != null){
            return new ResponseEntity("Email already in use.", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findFirstByusername(user.getUsername()) !=null){
            return new ResponseEntity("User already exists.", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity("Registered Successfully!", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody LoginDto loginDto){

        UserData user = userRepository.findFirstByusername(loginDto.getUsername());

        if (user == null || !passwordEncoder.matches(loginDto.getPassword(),user.getSaltedPassword())){
            return new ResponseEntity("No such user.", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity(new LoginConfirmDto(user), HttpStatus.OK);
    }





}
