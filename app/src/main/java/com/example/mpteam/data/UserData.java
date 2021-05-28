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
public class UserData implements Serializable {
    String userId, nickname;
    ArrayList<String> userPosts; //public 글만 사용예정
    ArrayList<Integer> cardIndex;

}
