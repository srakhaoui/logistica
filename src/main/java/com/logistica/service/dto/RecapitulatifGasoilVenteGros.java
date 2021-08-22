package com.logistica.service.dto;

import com.logistica.domain.enumeration.UniteGasoilGros;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

public class RecapitulatifGasoilVenteGros implements ICsvConvertible {
    private String client;
    private LocalDate dateVente;
    private String carburant;
    private Float quantite;
    private UniteGasoilGros unite;
    private Float prixVenteUnitaire;
    private Float prixVenteTotal;

    public RecapitulatifGasoilVenteGros(String client, LocalDate dateVente, String carburant, Float quantite, UniteGasoilGros unite, Float prixVenteUnitaire, Float prixVenteTotal) {
        this.client = client;
        this.dateVente = dateVente;
        this.carburant = carburant;
        this.quantite = quantite;
        this.unite = unite;
        this.prixVenteUnitaire = prixVenteUnitaire;
        this.prixVenteTotal = prixVenteTotal;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public UniteGasoilGros getUnite() {
        return unite;
    }

    public void setUnite(UniteGasoilGros unite) {
        this.unite = unite;
    }

    public Float getPrixVenteUnitaire() {
        return prixVenteUnitaire;
    }

    public void setPrixVenteUnitaire(Float prixVenteUnitaire) {
        this.prixVenteUnitaire = prixVenteUnitaire;
    }

    public Float getPrixVenteTotal() {
        return prixVenteTotal;
    }

    public void setPrixVenteTotal(Float prixVenteTotal) {
        this.prixVenteTotal = prixVenteTotal;
    }

    public static String csvHeader() {
        return "client;carburant;quantite;prixVenteUnitaire;prixVenteTotal;unite";
    }

    @Override
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(Optional.ofNullable(client).orElse("")).append(";")
            .append(Optional.ofNullable(carburant).orElse("")).append(";")
            .append(StringUtils.replaceChars(Double.toString(quantite), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(prixVenteUnitaire), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(prixVenteTotal), '.', ',')).append(";")
            .append(unite);
        return csv.toString();
    }
}
