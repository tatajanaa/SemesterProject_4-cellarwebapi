package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Time;

public class Co2 {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
    private Time time;
    private int hour;
    private double reading;

    public Co2() {

    }

    public Co2(Date date, Time time, double reading) {
        this.date = date;
        this.time = time;
        this.reading = reading;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public double getReading() {
        return reading;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    @Override
    public String toString() {
        return "Co2{" +
                "date=" + date +
                ", time=" + time +
                ", reading=" + reading +
                '}';
    }
}
