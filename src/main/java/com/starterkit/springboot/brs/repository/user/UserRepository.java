package com.starterkit.springboot.brs.repository.user;

import com.starterkit.springboot.brs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
