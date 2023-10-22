package com.example.gamesservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "news")
public class NewsPost {

    @Id
    private String id;
    private String header;
    private String text;
    private long postedTimestamp;

    public NewsPost(String header, String text, long postedTimestamp) {
        this.header = header;
        this.text = text;
        this.postedTimestamp = postedTimestamp;
    }

    public String getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getPostedTimestamp() {
        return postedTimestamp;
    }

    public void setPostedTimestamp(long postedTimestamp) {
        this.postedTimestamp = postedTimestamp;
    }
}
