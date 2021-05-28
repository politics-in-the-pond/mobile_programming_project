package com.example.mpteam.data;

import java.util.ArrayList;

public class UserData {
    String userId, nickname;
    ArrayList<String> userPosts; //public 글만 사용예정
    ArrayList<Integer> cardIndex;

    public UserData(String userId, String nickname, ArrayList<String> userPosts, ArrayList<Integer> cardIndex){
        this.userId = userId;
        this.nickname = nickname;
        this.userPosts = userPosts;
        this.cardIndex = cardIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(ArrayList<String> userPosts) {
        this.userPosts = userPosts;
    }

    public void addUserPosts(String s) {this.userPosts.add(s); }

    public ArrayList<Integer> getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(ArrayList<Integer> cardIndex) {
        this.cardIndex = cardIndex;
    }
}
