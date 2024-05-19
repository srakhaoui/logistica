package com.logistica.config;

import com.logistica.domain.enumeration.TypeLivraison;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "logistica.facturation", ignoreUnknownFields = false)
public class TvaProperties {
    private Map<TypeLivraison, Float> tva;

    public Map<TypeLivraison, Float> getTva() {
        return tva;
    }

    public void setTva(Map<TypeLivraison, Float> tva) {
        this.tva = tva;
    }
}
