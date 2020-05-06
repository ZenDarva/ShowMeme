package xyz.theasylum.showmeme.post.domain;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class UserData implements UserDetails {
    private String id;
    private String username;

//    @Setter(AccessLevel.NONE)
//    private List<String> authorities;

    public UserData(DecodedJWT user){
        username=user.getSubject();
    }
    public UserData(){}

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


//    public List<GrantedAuthority> getAuthorities(AuthorityRepository authorityRepository){
//        return authorityRepository.findByAuthorityIn(authorities).stream().map(f->((GrantedAuthority)f)).collect(Collectors.toList());
//    }
}
