package com.bancohipotecario.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bancohipotecario.dto.MotorBikePriceDTO;
import com.bancohipotecario.model.BrandAverage;
import com.bancohipotecario.model.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class MotorBikeService {

	@Autowired
	private RestTemplate restTemplate;



	private static final String API_CATEGORY_URL = "https://api.mercadolibre.com/sites/MLA/categories";

	private static final String API_SUB_CATEGORY_URL = "https://api.mercadolibre.com/categories";

	private static final String API_URL = "https://api.mercadolibre.com/sites/MLA/search?category={category}&condition=new&offset={offset}";

	private final ObjectMapper objectMapper;

	public MotorBikeService(RestTemplate restTemplate, RedisService redisService, ObjectMapper objectMapper) {
		super();
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public List<MotorBikePriceDTO> getAveragePricesByBrand() {
		Map<String, BrandAverage> brandDataMap = new HashMap<>();
		brandDataMap= this.calculate();
		List<MotorBikePriceDTO> motorBikePriceList = new ArrayList<>();
		 motorBikePriceList = brandDataMap.entrySet().stream()
			    .map(entry -> new MotorBikePriceDTO(entry.getKey(), entry.getValue().getFormattedAverage()))
			    .collect(Collectors.toList());
		
		
		return motorBikePriceList;

	}

	public Map<String, BrandAverage> calculate() {
		String categoryId = this.getMotorbikeCategory();
		String subcategoryId = this.getMotorbikeSubCategory(categoryId);
		List<CompletableFuture<String>> futures = new ArrayList<>();
		String paginatedUrl = API_URL.replace("{category}", subcategoryId);
		for (int i = 0; i < 18; i++) {
			int offset = i * 50;
			String url = UriComponentsBuilder.fromHttpUrl(paginatedUrl).queryParam("offset", offset).toUriString();
			CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
				return restTemplate.getForObject(url, String.class);
			});
			futures.add(future);
		}
		Map<String, BrandAverage> brandDataMap = new HashMap<>();

		try {
			CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
			allOf.get();
			

			futures.forEach(f -> {
				try {
					String response = f.get();

					this.calculateAverage(response, brandDataMap);
				
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return brandDataMap; 
	}

	public void calculateAverage(String responseString, Map<String, BrandAverage> brandDataMap) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Response response = objectMapper.readValue(responseString, Response.class);
			for (int i = 0; i < response.getResults().size(); i++) {
				for (int a = 0; a < response.getResults().get(i).getAttributes().size(); a++) {
					if (response.getResults().get(i).getAttributes().get(a).getId().equals("BRAND")) {
						String brand = response.getResults().get(i).getAttributes().get(a).getValueName();
						double price = response.getResults().get(i).getPrice();
						brandDataMap.putIfAbsent(brand, new BrandAverage());
						brandDataMap.get(brand).addPrice(price);
					}

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public String getMotorbikeCategory() {
		try {
			String response = restTemplate.getForObject(API_CATEGORY_URL, String.class);

			List<Map<String, String>> categories = objectMapper.readValue(response,
					new TypeReference<List<Map<String, String>>>() {
					});

			for (Map<String, String> category : categories) {
				if (category.get("name").toLowerCase().contains("moto")) {
					return category.get("id");
				}
			}

			return "Categoría de motos no encontrada";

		} catch (HttpClientErrorException e) {
			System.err.println("Error de cliente: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
			return "Error de cliente: " + e.getStatusCode();
		} catch (RestClientException e) {
			System.err.println("Error al realizar la solicitud: " + e.getMessage());
			return "Error en la solicitud";
		} catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			return "Error inesperado";
		}
	}

	public String getMotorbikeSubCategory(String categoryId) {
		try {
			String response = restTemplate.getForObject(API_SUB_CATEGORY_URL + "/" + categoryId, String.class);

			Map<String, Object> categoryResponse = objectMapper.readValue(response,
					new TypeReference<Map<String, Object>>() {
					});

			Object childrenCategoriesObject = categoryResponse.get("children_categories");

			if (childrenCategoriesObject instanceof List<?>) {
				List<?> list = (List<?>) childrenCategoriesObject;
				if (!list.isEmpty() && list.get(0) instanceof Map) {
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> childrenCategories = (List<Map<String, Object>>) list;
					for (Map<String, Object> category : childrenCategories) {
						if (category.get("name") != null
								&& category.get("name").toString().toLowerCase().contains("motos")) {
							return category.get("id").toString(); 
						}
					}
				}
			}

			return "Categoría de motos no encontrada"; 

		} catch (HttpClientErrorException e) {
			System.err.println("Error de cliente: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
			return "Error de cliente: " + e.getStatusCode();
		} catch (RestClientException e) {

			System.err.println("Error al realizar la solicitud: " + e.getMessage());
			return "Error en la solicitud";
		} catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			return "Error inesperado";
		}
	}

}