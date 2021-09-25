package com.logistica.service.dto;

public class ImportMailiResponse {

    private String message;
    private Integer bonsGasoil;

    public ImportMailiResponse() {
    }

    public ImportMailiResponse(String message, Integer bonsGasoil) {
        this.message = message;
        this.bonsGasoil = bonsGasoil;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getBonsGasoil() {
        return bonsGasoil;
    }

    public void setBonsGasoil(Integer bonsGasoil) {
        this.bonsGasoil = bonsGasoil;
    }
}
