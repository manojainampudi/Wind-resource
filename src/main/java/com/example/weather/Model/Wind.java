package com.example.weather.Model;

import java.io.Serializable;

public class Wind implements Serializable{

    private double speed;
    private int deg;
    private int gust;

    public Wind(double speed, int deg, int gust) {
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

    public Wind(){}

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getGust() {
        return gust;
    }

    public void setGust(int gust) {
        this.gust = gust;
    }
}
