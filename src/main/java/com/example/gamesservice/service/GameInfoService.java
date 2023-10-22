package com.example.gamesservice.service;

import com.example.gamesservice.model.GameInfo;
import com.example.gamesservice.repository.GameInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameInfoService {

    private final GameInfoRepository gameInfoRepository;

    public GameInfoService(GameInfoRepository gameInfoRepository) {
        this.gameInfoRepository = gameInfoRepository;
    }

    public List<GameInfo> findAll() {
        return gameInfoRepository.findAll();
    }

    public GameInfo createGameInfo(GameInfo target) {
        return gameInfoRepository.save(target);
    }

    public GameInfo findGameInfoById(String id) {
        return gameInfoRepository.findById(id).orElse(null);
    }

    public GameInfo findGameInfoByName(String name) {
        return gameInfoRepository.findByName(name.toLowerCase());
    }

    public GameInfo updateGameInfo(GameInfo gameInfo) {
        return gameInfoRepository.save(gameInfo);
    }

    public void deleteById(String id) {
        gameInfoRepository.deleteById(id);
    }

    public void deleteAll() {
        gameInfoRepository.deleteAll();
    }

}
