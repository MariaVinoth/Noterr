package com.example.shiv.cal.Noterr;

import java.util.Date;

/**
 * Created by Vinod on 05/11/17.
 */

public class Todo_main {


    private Integer ID ;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public Date getDate_crtd() {
        return Date_crtd;
    }

    public void setDate_crtd(Date date_crtd) {
        Date_crtd = date_crtd;
    }

    private String Desc;
    private String Priority;
    private Date Date_crtd;


}
