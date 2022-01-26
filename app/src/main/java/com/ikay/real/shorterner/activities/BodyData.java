package com.ikay.real.shorterner.activities;

import android.graphics.drawable.Drawable;

import de.hdodenhof.circleimageview.CircleImageView;

public class BodyData {
    public String host;
    public String link;
    public Drawable drawable;

    BodyData(String link){
        this.link = link;

    }
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
