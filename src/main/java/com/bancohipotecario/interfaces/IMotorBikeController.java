package com.bancohipotecario.interfaces;

import java.util.List;

import com.bancohipotecario.dto.MotorBikePriceDTO;

public interface IMotorBikeController {
    List<MotorBikePriceDTO> getAveragePricesByBrand();
    String getMotorBikeCategories();
    String getMotorBikeSubCategories(String category);
	void calculate();
}