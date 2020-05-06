package xyz.theasylum.showmeme.account.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.theasylum.showmeme.account.domain.UserData;

@Repository
public interface UserRepository extends MongoRepository<UserData, String> {

    UserData findFirstByusername(String username);
    UserData findFirstByEmail(String email);

}
