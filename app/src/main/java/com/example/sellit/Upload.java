package com.example.sellit;

import android.media.Image;

public class Upload {

    private String item_discription;
    private String item_price;
    private String username;
    private String item_name;
    private String downloaduri;
    private String Id;

    public Upload(){

    }
    public Upload(String item_discription, String item_price, String username, String item_name, String downloaduri, String id) {
        this.item_discription = item_discription;
        this.item_price = item_price;
        this.username = username;
        this.item_name = item_name;
        this.downloaduri = downloaduri;
        Id = id;
    }

    public String getItem_discription() {
        return item_discription;
    }

    public void setItem_discription(String item_discription) {
        this.item_discription = item_discription;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDownloaduri() {
        return downloaduri;
    }

    public void setDownloaduri(String downloaduri) {
        this.downloaduri = downloaduri;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}

