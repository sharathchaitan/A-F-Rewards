package com.example.aberchrombie.shar.retrofit;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import lombok.Data;

@Data
public class ViewModel {
    @SerializedName("title")
    private String title;
    @SerializedName("topDescription")
    private String topDescription;
    @SerializedName("bottomDescription")
    private String bottomDescription;
    @SerializedName("promoMessage")
    private String promoMessage;
    @SerializedName("backgroundImage")
    private String backGroundImage;
    @SerializedName("content")
    private JSONArray content;
}
