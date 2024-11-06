package com.bancohipotecario.model;

public class SalePrice {
    private String price_id;
    private int amount;
    private String currency_id;

    // Getters y Setters
    public String getPriceId() {
        return price_id;
    }

    public void setPriceId(String priceId) {
        this.price_id = priceId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrencyId() {
        return currency_id;
    }

    public void setCurrencyId(String currencyId) {
        this.currency_id = currencyId;
    }
}