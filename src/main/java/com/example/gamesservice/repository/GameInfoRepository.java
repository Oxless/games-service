package com.example.gamesservice.repository;

import com.example.gamesservice.model.GameInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameInfoRepository extends MongoRepository<GameInfo, String> {

    @Query("{ name : '?0' }")
    GameInfo findByName(String name);

}
