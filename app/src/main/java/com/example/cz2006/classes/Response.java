package com.example.cz2006.classes;

import java.util.List;

public class Response {
    private Userdata userdata;
    private List<WaterData> monthlyWater;
    private List<WaterData> dailyWater;
    private List<WaterData> hourlyWater;
    private List<ElectricityData> monthlyElectricity;
    private List<ElectricityData> dailyElectricity;
    private List<ElectricityData> hourlyElectricity;
    private Summary summary;

    public Response(Userdata userdata, List<WaterData> monthlyWater, List<WaterData> dailyWater, List<WaterData> hourlyWater, List<ElectricityData> monthlyElectricity, List<ElectricityData> dailyElectricity, List<ElectricityData> hourlyElectricity, Summary summary) {
        this.userdata = userdata;
        this.monthlyWater = monthlyWater;
        this.dailyWater = dailyWater;
        this.hourlyWater = hourlyWater;
        this.monthlyElectricity = monthlyElectricity;
        this.dailyElectricity = dailyElectricity;
        this.hourlyElectricity = hourlyElectricity;
        this.summary = summary;
    }

    public Userdata getUserData() {
        return userdata;
    }

    public List<WaterData> getMonthlyWater() {
        return monthlyWater;
    }

    public List<WaterData> getDailyWater() {
        return dailyWater;
    }

    public List<WaterData> getHourlyWater() {
        return hourlyWater;
    }

    public List<ElectricityData> getMonthlyElectricity() {
        return monthlyElectricity;
    }

    public List<ElectricityData> getDailyElectricity() {
        return dailyElectricity;
    }

    public List<ElectricityData> getHourlyElectricity() {
        return hourlyElectricity;
    }

    public Summary getSummary() {
        return summary;
    }
}
