package com.logistica.service.dto;

import com.logistica.domain.enumeration.UniteGasoilGros;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

public interface IRecapitulatifGasoilTransactionGros extends ICsvConvertible {

    String getFournisseur();

    String getAcheteur();

    String getCarburant();

    LocalDate getDateReception();

    Float getQuantiteAchetee();

    UniteGasoilGros getUniteAchat();

    Float getPrixAchatUnitaire();

    Float getPrixAchatTotal();

    String getClient();

    String getTransporteur();

    LocalDate getDateVente();

    Float getQuantiteVendue();

    UniteGasoilGros getUniteVente();

    Float getPrixVenteUnitaire();

    Float getPrixVenteTotal();

    Float getMargeGlobale();

    Float getTauxMarge();

    String CSV_HEADER = "fournisseur;acheteur;carburant;dateReception;quantiteAchetee;uniteAchat;prixAchatUnitaire;prixAchatTotal;client;transporteur;dateVente;quantiteVendue;uniteVente;prixVenteUnitaire;prixVenteTotal;margeGlobale;tauxMarge";

    @Override
    default String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(getFournisseur()).append(";")
            .append(getAcheteur()).append(";")
            .append(getCarburant()).append(";")
            .append(getDateReception()).append(";")
            .append(StringUtils.replaceChars(Double.toString(getQuantiteAchetee()), '.', ',')).append(";")
            .append(getUniteAchat()).append(";")
            .append(StringUtils.replaceChars(Double.toString(getPrixAchatUnitaire()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getPrixAchatTotal()), '.', ',')).append(";")
            .append(Optional.ofNullable(getClient()).orElse("")).append(";")
            .append(Optional.ofNullable(getTransporteur()).orElse("")).append(";")
            .append(getDateVente()).append(";")
            .append(StringUtils.replaceChars(Double.toString(getQuantiteVendue()), '.', ',')).append(";")
            .append(getUniteVente()).append(";")
            .append(StringUtils.replaceChars(Double.toString(getPrixVenteUnitaire()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getPrixVenteTotal()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getMargeGlobale()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getTauxMarge()), '.', ','));
        return csv.toString();
    }
}
