package com.example.mpteam.data;

public class CardData {
    String title;
    String content;
    int number;

    public CardData(String title, String content, int number) {
        this.title = title;
        this.content = content;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
