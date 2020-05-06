package xyz.theasylum.showmeme.account.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import xyz.theasylum.showmeme.account.domain.dto.UserDto;
import xyz.theasylum.showmeme.account.repositories.AuthorityRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Document("user")
@Data
public class UserData {
    @Id
    private String id;

    @Indexed(name="username", unique =true)
    private String username;
    @Indexed(name="email", unique=true)
    private String email;
    private String saltedPassword;

    @Setter(AccessLevel.NONE)
    private List<String> authorities;


    public UserData(UserDto dto, BCryptPasswordEncoder encoder){
        this.username=dto.getUsername();
        this.email=dto.getEmail();
        this.saltedPassword=encoder.encode(dto.getPassword());
        this.authorities= new LinkedList<>();
        this.authorities.add("ROLE_USER");
    }
    public UserData(){

    }

    public List<GrantedAuthority> getAuthorities(AuthorityRepository authorityRepository){
        return authorityRepository.findByAuthorityIn(authorities).stream().map(f->((GrantedAuthority)f)).collect(Collectors.toList());
    }
}
