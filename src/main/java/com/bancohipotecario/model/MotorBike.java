package com.bancohipotecario.model;

public class MotorBike {
 
	private String id;
    private String brand;
    private Double price;
    private boolean isNew;
    
    public MotorBike(String id, String brand, Double price, boolean isNew) {
 		super();
 		this.id = id;
 		this.brand = brand;
 		this.price = price;
 		this.isNew = isNew;
 	}
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

    // Getters y Setters
}