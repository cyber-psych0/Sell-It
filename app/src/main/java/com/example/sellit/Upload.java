package com.example.sellit;

import android.media.Image;

public class Upload {

    private String mItemName;
    private String mPrice;
    private String mDescription;
    private String mImageUrl;

    public Upload(){
        //Empty Constructor
    }

    public Upload(String ItemName, String Price, String Description, String ImageUrl){

        mItemName = ItemName;
        mPrice = Price;
        mDescription = Description;
        mImageUrl = ImageUrl;
    }

    public String getmItemName() {
        return mItemName;
    }


    public String getmPrice() {
        return mPrice;
    }


    public String getmDescription() {
        return mDescription;
    }


    public String getmImageUrl() {
        return mImageUrl;
    }

}
