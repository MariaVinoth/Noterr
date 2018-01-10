package com.example.shiv.cal.Noterr;

import java.util.Date;

/**
 * Created by Shiv on 2017-11-25.
 */

public class Cal_shed_retrival {
    private long id;
    private String Name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getStart_Datetime() {
        return start_Datetime;
    }

    public void setStart_Datetime(Date start_Datetime) {
        this.start_Datetime = start_Datetime;
    }

    public Date getEnd_Datetime() {
        return end_Datetime;
    }

    public void setEnd_Datetime(Date end_Datetime) {
        this.end_Datetime = end_Datetime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    private Date start_Datetime;
    private Date end_Datetime;
    private String venue;
}
