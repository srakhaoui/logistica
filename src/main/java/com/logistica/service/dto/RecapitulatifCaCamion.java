package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;
import org.apache.commons.lang3.StringUtils;

public class RecapitulatifCaCamion implements ICsvConvertible {
    private String camion;
    private Unite uniteVente;
    private Double totalQuantiteVendue;
    private Double totalPrixVente;

    public RecapitulatifCaCamion(String camion, Unite uniteVente, Double totalQuantiteVendue, Double totalPrixVente) {
        this.camion = camion;
        this.uniteVente = uniteVente;
        this.totalQuantiteVendue = totalQuantiteVendue;
        this.totalPrixVente = totalPrixVente;
    }

    public String getCamion() {
        return camion;
    }

    public Unite getUniteVente() {
        return uniteVente;
    }

    public Double getTotalQuantiteVendue() {
        return totalQuantiteVendue;
    }

    public Double getTotalPrixVente() {
        return totalPrixVente;
    }

    public static String csvHeader() {
        return "camion;uniteVente;totalQuantiteVendue;totalPrixVente";
    }

    @Override
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(camion).append(";")
            .append(uniteVente).append(";")
            .append(StringUtils.replaceChars(Double.toString(totalQuantiteVendue), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(totalPrixVente), '.', ','));
        return csv.toString();
    }
}
