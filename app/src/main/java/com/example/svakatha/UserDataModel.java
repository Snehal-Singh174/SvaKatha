package com.example.svakatha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserDataModel {
    String name, totalLikes;
    int photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(String totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {  this.photo = photo;
    }
}
