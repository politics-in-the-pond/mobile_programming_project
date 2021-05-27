package com.example.mpteam.data;

import java.util.ArrayList;

public class PostData {
    String postId, userId, title, content, dateTime;
    double latitude, longitude;
    boolean ispublic;
    ArrayList<String> imageURL;
    int emotion; //0기쁨 1보통 2우울 3미묘 이런 형태로

    public PostData(String postId, String userId, String title, String content, String dateTime, double latitude, double longitude, boolean ispublic, ArrayList<String> imageURL, int emotion) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ispublic = ispublic;
        this.imageURL = imageURL;
        this.emotion = emotion;
    }

    public String getPostId() {
        return postId;
    }

    public boolean isIspublic() {
        return ispublic;
    }

    public void setIspublic(boolean ispublic) {
        this.ispublic = ispublic;
    }

    public void setPublicKey(boolean ispublic) {
        this.ispublic = ispublic;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
