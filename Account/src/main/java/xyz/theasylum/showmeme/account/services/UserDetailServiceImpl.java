package xyz.theasylum.showmeme.account.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.theasylum.showmeme.account.domain.Authority;
import xyz.theasylum.showmeme.account.domain.UserData;
import xyz.theasylum.showmeme.account.repositories.AuthorityRepository;
import xyz.theasylum.showmeme.account.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    public UserDetailServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.findFirstByusername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        List<String> permissions = new ArrayList<>();
        permissions.add("ROLE_ADMIN");
        return new User(
                user.getUsername(),
                user.getSaltedPassword(),
                user.getAuthorities(authorityRepository)
        );
    }

    @PostConstruct
    private void setupRoles(){
        if (authorityRepository.findFirstByAuthorityIs("ROLE_USER") == null){
            authorityRepository.save(new Authority("ROLE_USER"));
            authorityRepository.save(new Authority("ROLE_ADMIN"));
            authorityRepository.save(new Authority("ROLE_MOD"));
        }
    }


}
