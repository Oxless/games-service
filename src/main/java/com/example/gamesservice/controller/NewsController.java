package com.example.gamesservice.controller;

import com.example.gamesservice.model.NewsPost;
import com.example.gamesservice.repository.NewsRepository;
import com.example.gamesservice.util.ResponseMessages;
import com.example.gamesservice.util.SessionValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsRepository newsRepository;
    private final SessionValidator sessionValidator;

    public NewsController(NewsRepository newsRepository, SessionValidator sessionValidator) {
        this.newsRepository = newsRepository;
        this.sessionValidator = sessionValidator;
    }

    @GetMapping
    public ResponseEntity<Object> findAll(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, user -> ResponseEntity.ok(newsRepository.findAll().stream()
                .sorted(Comparator.comparingLong(NewsPost::getPostedTimestamp).reversed())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findNews(@PathVariable String id, HttpSession httpSession) {
        return sessionValidator.validate(httpSession, user -> ResponseEntity.ok(newsRepository.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Object> createNews(@RequestBody NewsPost newsPost, HttpSession httpSession) {
        return sessionValidator.validate(httpSession, user -> {
            if(user.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);

            newsRepository.save(newsPost);
            return ResponseEntity.ok(newsRepository.findAll());
        });
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> editNews(@PathVariable String id, @RequestBody NewsPost newsPost, HttpSession httpSession) {
        return sessionValidator.validate(httpSession, user -> {
            if(user.getRole().equals("user")) {
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);
            }
            Optional<NewsPost> newsPostOptional = newsRepository.findById(id);
            if(newsPostOptional.isPresent()) {
                NewsPost post = newsPostOptional.get();
                if(newsPost.getHeader() != null)
                    post.setHeader(newsPost.getHeader());
                if(newsPost.getText() != null)
                    post.setText(newsPost.getText());
                newsRepository.save(post);
            } else {
                return ResponseEntity.status(404).body(ResponseMessages.NOT_FOUND);
            }
            return ResponseEntity.ok(newsRepository.findAll());
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNews(@PathVariable String id, HttpSession httpSession) {
        return sessionValidator.validate(httpSession, user -> {
            if(user.getRole().equals("user")) {
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);
            }
            newsRepository.deleteById(id);
            return ResponseEntity.ok(newsRepository.findAll());
        });
    }

}
