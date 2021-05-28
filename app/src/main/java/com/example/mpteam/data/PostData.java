package com.example.mpteam.data;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostData {
    String postId, userId, title, content, dateTime;
    double latitude, longitude;
    boolean ispublic;
    ArrayList<String> imageURL;
    int emotion; //0기쁨 1보통 2우울 3미묘 이런 형태로
}
