package com.example.shiv.cal.Noterr;

import java.util.Date;

/**
 * Created by Vinod on 05/11/17.
 */

public class Notes_main {
    private Long ID;

    private String Desc;
    private Date Date_crtd;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Date getDate_crtd() {
        return Date_crtd;
    }

    public void setDate_crtd(Date date_crtd) {
        Date_crtd = date_crtd;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getBg_color() {
        return Bg_color;
    }

    public void setBg_color(String bg_color) {
        Bg_color = bg_color;
    }


    private String Tag;
    private String Bg_color;
}
