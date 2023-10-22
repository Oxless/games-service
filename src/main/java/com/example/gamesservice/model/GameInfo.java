package com.example.gamesservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "games")
public class GameInfo {

    @Id
    private String id;
    private String name;
    private String description;
    private String gameUrl;
    private List<String> genres;
    private String releaseDate;
    private String developer;

    public GameInfo(String name, String description, String gameUrl, List<String> genres, String releaseDate, String developer) {
        this.name = name;
        this.description = description;
        this.gameUrl = gameUrl;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.developer = developer;
    }

    public GameInfo() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
