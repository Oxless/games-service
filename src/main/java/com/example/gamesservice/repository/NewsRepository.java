package com.example.gamesservice.repository;

import com.example.gamesservice.model.NewsPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<NewsPost, String> {

}
