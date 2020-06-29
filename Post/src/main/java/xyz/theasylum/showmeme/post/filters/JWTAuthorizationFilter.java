package xyz.theasylum.showmeme.post.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import xyz.theasylum.showmeme.post.domain.Authority;
import xyz.theasylum.showmeme.post.domain.UserData;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static xyz.theasylum.showmeme.post.domain.Constants.SecurityConstants.*;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            SecurityContextHolder.getContext().setAuthentication(getAnnonUser());
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
    private UsernamePasswordAuthenticationToken getAnnonUser(){
        UserData data = new UserData();
        data.setId("Coward");
        return new UsernamePasswordAuthenticationToken(data, null, null);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            DecodedJWT user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));
            UserData userData = new UserData(user);
            if (userData != null) {
                return new UsernamePasswordAuthenticationToken(user.getSubject(), null, getRolesFromJWT(user));
            }
            return null;
        }
        return null;
    }
    private Collection getRolesFromJWT(DecodedJWT jwt) {
        List<Authority> authList = new LinkedList<>();
        if (!jwt.getClaims().containsKey("roles"))
            return authList;
        for (String role : jwt.getClaim("roles").asArray(String.class)) {
            authList.add(new Authority(role));
        }
        return authList;
    }
}
