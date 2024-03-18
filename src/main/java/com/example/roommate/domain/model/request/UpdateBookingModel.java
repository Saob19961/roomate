package com.example.roommate.domain.model.request;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class UpdateBookingModel implements Serializable {
    private String book_id;
    private Date date;
    private Time time_from;
    private Time time_to;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
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
}
