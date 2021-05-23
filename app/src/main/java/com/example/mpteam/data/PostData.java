package com.example.mpteam.data;

import java.util.ArrayList;

public class PostData {
    String postId, userId, title, content;
    ArrayList<String> imageURL;
    int emotion; //0기쁨 1보통 2우울 3미묘 이런 형태로

    public PostData(String postId, String userId, String title, String content, ArrayList<String> imageURL, int emotion) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.emotion = emotion;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public ArrayList<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(ArrayList<String> imageURL) {
        this.imageURL = imageURL;
    }

    public int getEmotion() {
        return emotion;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }
}
