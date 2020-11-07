package com.logistica.service.dto;

public class GasoilPriceResponse {

    private Float price;

    public GasoilPriceResponse() {
    }

    public GasoilPriceResponse(Float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
