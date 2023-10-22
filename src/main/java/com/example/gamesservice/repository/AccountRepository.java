package com.example.gamesservice.repository;

import com.example.gamesservice.model.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<UserAccount, String> {

    @Query("{ username : '?0' }")
    UserAccount findByName(String name);

}
