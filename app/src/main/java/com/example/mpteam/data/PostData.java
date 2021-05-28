package com.example.mpteam.data;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostData implements Serializable {
    //String postId;
    String userId, title, content, dateTime;
    double latitude, longitude;
    boolean ispublic;
    ArrayList<String> imageURL;
    int emotion; //0기쁨 1보통 2우울 3미묘 이런 형태로

    public void addImageURL(String s){
        this.imageURL.add(s);
    }

}
