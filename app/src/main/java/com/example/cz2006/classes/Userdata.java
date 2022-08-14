package com.example.cz2006.classes;

public class Userdata {
    private String electricitySupplier;
    private int waterBudget;
    private int electricityBudget;

    public Userdata(String electricitySupplier, int waterBudget, int electricityBudget) {
        this.electricitySupplier = electricitySupplier;
        this.waterBudget = waterBudget;
        this.electricityBudget = electricityBudget;
    }

    public String getElectricitySupplier() {
        return electricitySupplier;
    }

    public int getWaterBudget() {
        return waterBudget;
    }

    public int getElectricityBudget() {
        return electricityBudget;
    }

    public void setElectricitySupplier(String electricitySupplier) {
        this.electricitySupplier = electricitySupplier;
    }

    public void setWaterBudget(int waterBudget) {
        this.waterBudget = waterBudget;
    }

    public void setElectricityBudget(int electricityBudget) {
        this.electricityBudget = electricityBudget;
    }
}
