package xyz.theasylum.showmeme.account.domain.dto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import xyz.theasylum.showmeme.account.domain.UserData;

import java.util.Date;

import static xyz.theasylum.showmeme.account.domain.Constants.SecurityConstants.EXPIRATION_TIME;
import static xyz.theasylum.showmeme.account.domain.Constants.SecurityConstants.SECRET;

@Data
public class LoginConfirmDto {
    private String username;
    private String id;
    private String accessToken;
    private String email;
    private String[] roles;

    public LoginConfirmDto(UserData user){
        this.username=user.getUsername();
        this.id=user.getId();
        this.accessToken= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withArrayClaim("roles",user.getAuthorities().stream().filter(f->f.startsWith("ROLE")).toArray(String[]::new))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        this.email=user.getEmail();
        this.roles=user.getAuthorities().stream().filter(f->f.startsWith("ROLE")).toArray(String[]::new);
    }
}
