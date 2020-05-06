package xyz.theasylum.showmeme.storage.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static xyz.theasylum.showmeme.storage.domain.Constants.SecurityConstants.*;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //TODO: Fixme.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String header = request.getHeader(HEADER_STRING);
//        if (header == null || !header.startsWith(TOKEN_PREFIX)){
//            chain.doFilter(request,response);
//            return;
//        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        return new UsernamePasswordAuthenticationToken("annon",null,null);
//        String token = request.getHeader(HEADER_STRING);
//        if (token != null){
//            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
//                    .build()
//                    .verify(token.replace(TOKEN_PREFIX, ""))
//                    .getSubject();
//            if (user != null){
//                UserDetails userData = userDetailsService.loadUserByUsername(user);
//                return new UsernamePasswordAuthenticationToken(user,null, userData.getAuthorities());
//            }
//            return null;
//        }
//        return null;
    }
}
