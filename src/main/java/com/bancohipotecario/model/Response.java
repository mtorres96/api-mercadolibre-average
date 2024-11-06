package com.bancohipotecario.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private String country_default_timezone;
    private Paging paging;
    private List<Result> results;

  

    public String getCountryDefaultTimeZone() {
        return country_default_timezone;
    }

    public void setCountryDefaultTimeZone(String countryDefaultTimeZone) {
        this.country_default_timezone = countryDefaultTimeZone;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
    

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
    
}
