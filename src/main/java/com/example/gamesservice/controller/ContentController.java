package com.example.gamesservice.controller;

import com.example.gamesservice.model.GameInfo;
import com.example.gamesservice.service.GameInfoService;
import com.example.gamesservice.util.ResponseMessages;
import com.example.gamesservice.util.SessionValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class ContentController {

    private final GameInfoService gameInfoService;
    private final SessionValidator sessionValidator;

    public ContentController(GameInfoService gameInfoService, SessionValidator sessionValidator) {
        this.gameInfoService = gameInfoService;
        this.sessionValidator = sessionValidator;
    }

    @GetMapping
    public ResponseEntity<Object> allGames(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, userAccount -> ResponseEntity.ok(gameInfoService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGame(HttpSession httpSession, @PathVariable String id) {
        return sessionValidator.validate(httpSession, userAccount -> {
            GameInfo gameInfo = gameInfoService.findGameInfoById(id);
            if(gameInfo == null)
                return ResponseEntity.status(404).body(ResponseMessages.NOT_FOUND);

            return ResponseEntity.ok(gameInfo);
        });
    }

    @PostMapping
    public ResponseEntity<Object> createGame(HttpSession httpSession, @RequestBody GameInfo gameInfo) {
        return sessionValidator.validate(httpSession, userAccount -> {
            if(userAccount.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);

            return ResponseEntity.ok(gameInfoService.createGameInfo(gameInfo));
        });
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateGame(HttpSession httpSession, @PathVariable String id, @RequestBody GameInfo gameInfo) {
        return sessionValidator.validate(httpSession, userAccount -> {
            if(userAccount.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);

            GameInfo target = gameInfoService.findGameInfoById(id);
            if(target == null)
                return ResponseEntity.status(404).body(ResponseMessages.NOT_FOUND);

            String name = gameInfo.getName();
            if(name != null)
                target.setName(name);

            String description = gameInfo.getDescription();
            if(description != null)
                target.setDescription(description);

            String gameUrl = gameInfo.getGameUrl();
            if(gameUrl != null)
                target.setGameUrl(gameUrl);

            String developer = gameInfo.getDeveloper();
            if(developer != null)
                target.setDeveloper(developer);

            String releaseDate = gameInfo.getReleaseDate();
            if(releaseDate != null)
                target.setReleaseDate(releaseDate);

            List<String> genres = gameInfo.getGenres();
            if(genres != null)
                target.setGenres(genres);

            gameInfoService.updateGameInfo(target);
            return ResponseEntity.ok(gameInfoService.findAll());
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGame(HttpSession httpSession, @PathVariable String id) {
        return sessionValidator.validate(httpSession, userAccount -> {
            if(userAccount.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);

            GameInfo target = gameInfoService.findGameInfoById(id);
            if(target == null)
                return ResponseEntity.status(404).body(ResponseMessages.NOT_FOUND);

            gameInfoService.deleteById(id);
            return ResponseEntity.ok("successfully deleted");
        });
    }

    @DeleteMapping("/all")
    public ResponseEntity<Object> deleteAllGames(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, userAccount -> {
            if(userAccount.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);

            gameInfoService.deleteAll();
            return ResponseEntity.ok("successfully deleted");
        });
    }

}
