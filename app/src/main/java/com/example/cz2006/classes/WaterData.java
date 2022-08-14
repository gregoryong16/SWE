package com.example.cz2006.classes;

public class WaterData {
    private int date;
    private float washingMachine;
    private float toiletFlush;
    private float shower;
    private float taps;

    public WaterData(int date, float washingMachine, float toiletFlush, float shower, float taps) {
        this.date = date;
        this.washingMachine = washingMachine;
        this.toiletFlush = toiletFlush;
        this.shower = shower;
        this.taps = taps;
    }

    public int getDate() {
        return date;
    }

    public float getWashingMachine() {
        return washingMachine;
    }

    public float getToiletFlush() {
        return toiletFlush;
    }

    public float getShower() {
        return shower;
    }

    public float getTaps() {
        return taps;
    }
}
