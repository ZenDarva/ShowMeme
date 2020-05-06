package xyz.theasylum.showmeme.account.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.theasylum.showmeme.account.domain.Authority;

import java.util.Collection;
import java.util.List;

public interface AuthorityRepository extends MongoRepository<Authority,String> {

    Authority findFirstByAuthorityIs(String authority);
    List<Authority> findByAuthorityIn(Collection<String> authorities);
}
