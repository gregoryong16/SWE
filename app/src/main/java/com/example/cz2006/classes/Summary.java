package com.example.cz2006.classes;

public class Summary {
    private float waterUsage;
    private float waterCost;
    private float electricityUsage;
    private float electricityCost;
    private float waterRemaining;
    private float electricityRemaining;

    public Summary(float waterUsage, float waterCost, float electricityUsage, float electricityCost, float waterRemaining, float electricityRemaining) {
        this.waterUsage = waterUsage;
        this.waterCost = waterCost;
        this.electricityUsage = electricityUsage;
        this.electricityCost = electricityCost;
        this.waterRemaining = waterRemaining;
        this.electricityRemaining = electricityRemaining;
    }

    public float getWaterUsage() {
        return waterUsage;
    }

    public float getWaterCost() {
        return waterCost;
    }

    public float getElectricityUsage() {
        return electricityUsage;
    }

    public float getElectricityCost() {
        return electricityCost;
    }

    public float getWaterRemaining() {
        return waterRemaining;
    }

    public float getElectricityRemaining() {
        return electricityRemaining;
    }
}
