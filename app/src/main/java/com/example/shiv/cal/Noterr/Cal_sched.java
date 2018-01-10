package com.example.shiv.cal.Noterr;

import java.util.Date;

/**
 * Created by Vinod on 05/11/17.
 */

public class Cal_sched {
    private Long ID;
    private String Name;
    private Date Date_time;

    private String Venue;
    private Date Date_crtd;


    public Date getDate_time() {
        return Date_time;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setDate_time(Date date_time) {
        Date_time = date_time;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }


    public Date getDate_crtd() {
        return Date_crtd;
    }

    public void setDate_crtd(Date date_crtd) {
        Date_crtd = date_crtd;
    }


}
