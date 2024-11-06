package com.bancohipotecario.dto;

public class MotorBikePriceDTO {
	
	private String brand;
    private String averagePrice;
    
    public MotorBikePriceDTO(String brand, String averagePrice) {
		super();
		this.brand = brand;
		this.averagePrice = averagePrice;
	}
	
    public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}


    // Getters y Setters
}
