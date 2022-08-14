package com.example.cz2006.classes;

public class ElectricityData {
    private int date;
    private float aircon;
    private float fridge;
    private float tv;
    private float waterHeater;
    private float misc;

    public ElectricityData(int date, float aircon, float fridge, float tv, float waterHeater, float misc) {
        this.date = date;
        this.aircon = aircon;
        this.fridge = fridge;
        this.tv = tv;
        this.waterHeater = waterHeater;
        this.misc = misc;
    }

    public int getDate() {
        return date;
    }

    public float getAirCon() {
        return aircon;
    }

    public float getFridge() {
        return fridge;
    }

    public float getTv() {
        return tv;
    }

    public float getWaterHeater() {
        return waterHeater;
    }

    public float getMisc() {
        return misc;
    }
}
