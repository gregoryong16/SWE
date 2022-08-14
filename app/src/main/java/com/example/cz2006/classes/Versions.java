package com.example.cz2006.classes;

public class Versions {
    private String applianceName, cost, amountUsed, tips;
    private boolean expandable;

    public Versions(String applianceName, String cost, String amountUsed, String tips) {
        this.applianceName = applianceName;
        this.cost = cost;
        this.amountUsed = amountUsed;
        this.tips = tips;
        this.expandable = false;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(String amountUsed) {
        this.amountUsed = amountUsed;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    @Override
    public String toString() {
        return "Versions{" +
                "applianceName='" + applianceName + '\'' +
                ", cost='" + cost + '\'' +
                ", amountUsed='" + amountUsed + '\'' +
                ", tips='" + tips + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
