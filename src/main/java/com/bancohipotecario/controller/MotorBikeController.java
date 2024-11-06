package com.bancohipotecario.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bancohipotecario.dto.MotorBikePriceDTO;
import com.bancohipotecario.interfaces.IMotorBikeController;
import com.bancohipotecario.services.MotorBikeService;
import com.bancohipotecario.services.RedisService;

import java.util.List;

@Component
public class MotorBikeController implements IMotorBikeController {

    @Autowired
    private MotorBikeService motoService;

    @Autowired
    private RedisService redisService;

    @Override
    public List<MotorBikePriceDTO> getAveragePricesByBrand() {
    	
        List<MotorBikePriceDTO> averagePrices = redisService.getAveragePrices();
        
        if (averagePrices != null) {
        	return averagePrices;
        }
        averagePrices = motoService.getAveragePricesByBrand();
        redisService.saveAveragePrices(averagePrices);
        return motoService.getAveragePricesByBrand();
    }


	@Override
	public String getMotorBikeCategories() {
		return motoService.getMotorbikeCategory();
	}
	
	@Override
	public void calculate() {
		motoService.calculate();
	}
	
	@Override
	public String getMotorBikeSubCategories(String category) {
		return motoService.getMotorbikeSubCategory(category);
	}
}