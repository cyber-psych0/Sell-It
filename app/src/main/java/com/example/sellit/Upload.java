package com.example.sellit;

import android.media.Image;

public class Upload {

    private String mItemName;
    private String mPrice;
    private String mDescription;
    private String mImageUrl;
    private String uploader_name;

    public Upload(){
        //Empty Constructor
    }

    public Upload(String ItemName, String Price, String Description, String ImageUrl,String uploaderName){

        mItemName = ItemName;
        mPrice = Price;
        mDescription = Description;
        mImageUrl = ImageUrl;
        uploader_name =  uploaderName;
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

    public String getUploader_name() {
        return uploader_name;
    }
}
