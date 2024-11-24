package com.example.imdb;

import java.io.Serializable;

public class Report implements Serializable {
    private String title;
    private String content;
    private String senderName;
    private String category;
    private String timestamp;

    public Report(String title, String content, String senderName, String category, String timestamp) {
        this.title = title;
        this.content = content;
        this.senderName = senderName;
        this.category = category;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getCategory() {
        return category;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
