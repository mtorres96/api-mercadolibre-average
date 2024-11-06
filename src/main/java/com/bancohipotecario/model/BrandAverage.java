package com.bancohipotecario.model;

public class BrandAverage {
    private int count;
    private double total;

    public void addPrice(double price) {
        total += price;
        count++;
    }

    public double getAverage() {
        return count == 0 ? 0 : total / count;
    }
    
    public String getFormattedAverage() {
        return count == 0 ? "0.00" : String.format("%.2f", total / count);
    }

}
