package com.logistica.service.dto;

import com.logistica.domain.enumeration.UniteGasoilGros;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class RecapitulatifGasoilAchatGros implements ICsvConvertible {
    private String description;
    private String fournisseur;
    private String acheteur;
    private String carburant;
    private LocalDate dateReception;
    private Float quantity;
    private UniteGasoilGros unite;
    private Float prixUnitaire;


    public RecapitulatifGasoilAchatGros(String description, String fournisseur, String acheteur, String carburant, LocalDate dateReception, Float quantity, UniteGasoilGros unite, Float prixUnitaire) {
        this.description = description;
        this.fournisseur = fournisseur;
        this.acheteur = acheteur;
        this.carburant = carburant;
        this.dateReception = dateReception;
        this.quantity = quantity;
        this.unite = unite;
        this.prixUnitaire = prixUnitaire;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public UniteGasoilGros getUnite() {
        return unite;
    }

    public void setUnite(UniteGasoilGros unite) {
        this.unite = unite;
    }

    public Float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public static String csvHeader() {
        return "description;fournisseur;acheteur;carburant;dateReception;quantity;unite;prixUnitaire;";
    }

    @Override
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(description).append(";")
            .append(fournisseur).append(";")
            .append(acheteur).append(";")
            .append(carburant).append(";")
            .append(dateReception).append(";")
            .append(StringUtils.replaceChars(Double.toString(quantity), '.', ',')).append(";")
            .append(unite).append(";")
            .append(StringUtils.replaceChars(Double.toString(prixUnitaire), '.', ','));

        return csv.toString();
    }
}
