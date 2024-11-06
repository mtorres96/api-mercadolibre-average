package com.bancohipotecario.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {
    private String id;
    private String name;
    @JsonProperty("value_name")
    private String value_name;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueName() {
        return value_name;
    }

    public void setValueName(String valueName) {
        this.value_name = valueName;
    }
}
