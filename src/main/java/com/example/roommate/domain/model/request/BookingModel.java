package com.example.roommate.domain.model.request;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class BookingModel implements Serializable {
    private String user_id;
    private String arbeitplatz_id;
    private Date date;
    private Time time_from;
    private Time time_to;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getArbeitplatz_id() {
        return arbeitplatz_id;
    }

    public void setArbeitplatz_id(String arbeitplatz_id) {
        this.arbeitplatz_id = arbeitplatz_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime_from() {
        return time_from;
    }

    public void setTime_from(Time time_from) {
        this.time_from = time_from;
    }

    public Time getTime_to() {
        return time_to;
    }

    public void setTime_to(Time time_to) {
        this.time_to = time_to;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "user_id='" + user_id + '\'' +
                ", arbeitplatz_id='" + arbeitplatz_id + '\'' +
                ", date=" + date +
                ", time_from=" + time_from +
                ", time_to=" + time_to +
                '}';
    }
}
