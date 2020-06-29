package xyz.theasylum.showmeme.post.domain;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private String authority;
    @Id
    private String id;

    @Override
    public String getAuthority() {
        return authority;
    }

    public Authority(String authority){
        this.authority=authority;
    }

    public Authority(){}
}
