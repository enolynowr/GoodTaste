package com.goodtaste.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by LG Electronics on 2017/08/20.
 */
//APIのjasonのデ-タ項目
public class GoodTasteResponseModel {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String  name;
    @SerializedName("friends")
    private ArrayList<String> friends;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
}
