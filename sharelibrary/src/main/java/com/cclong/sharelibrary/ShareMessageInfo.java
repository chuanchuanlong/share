package com.cclong.sharelibrary;

import android.graphics.Bitmap;

public class ShareMessageInfo {

    public ShareMessageData data;

    public static class ShareMessageData {
        public String title;
        public String link;
        public String shortLink;
        public String message;
        public String image;
        public Bitmap bitmap;
        public int resId;
    }
}