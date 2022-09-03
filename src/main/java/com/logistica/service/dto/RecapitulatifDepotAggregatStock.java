package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecapitulatifDepotAggregatStock {
    private final String nom;
    private final Float initialStock;
    private Unite uniteIntialStock;
    private List<StockDepot> achats;
    private List<StockDepot> ventes;
    private List<StockDepot> transfertsEntrants;
    private List<StockDepot> transfertsSortants;
    private Map<Unite, Float> stockByUnite;

    public RecapitulatifDepotAggregatStock(String nom, Float initialStock, Unite uniteIntialStock) {
        stockByUnite = new HashMap<>();
        this.nom = nom;
        this.initialStock = initialStock;
        this.uniteIntialStock = uniteIntialStock;
    }

    public String getNom() {
        return nom;
    }

    public RecapitulatifDepotAggregatStock achats(List<StockDepot> achats) {
        this.achats = achats;
        achats.stream()
            .forEach(achat -> stockByUnite.put(achat.getUnite(), stockByUnite.getOrDefault(achat.getUnite(), 0.0F) + achat.getTotal()));
        return this;
    }

    public RecapitulatifDepotAggregatStock ventes(List<StockDepot> ventes) {
        this.ventes = ventes;
        ventes.stream()
            .forEach(vente -> stockByUnite.put(vente.getUnite(), stockByUnite.getOrDefault(vente.getUnite(), 0.0F) - vente.getTotal()));
        return this;
    }

    public RecapitulatifDepotAggregatStock transfertsEntrants(List<StockDepot> transfertsEntrants) {
        this.transfertsEntrants = transfertsEntrants;
        transfertsEntrants.stream()
            .forEach(transfertEntrant -> stockByUnite.put(transfertEntrant.getUnite(), stockByUnite.getOrDefault(transfertEntrant.getUnite(), 0.0F) + transfertEntrant.getTotal()));
        return this;
    }

    public RecapitulatifDepotAggregatStock transfertsSortants(List<StockDepot> transfertsSortants) {
        this.transfertsSortants = transfertsSortants;
        transfertsSortants.stream()
            .forEach(transfertSortant -> stockByUnite.put(transfertSortant.getUnite(), stockByUnite.getOrDefault(transfertSortant.getUnite(), 0.0F) - transfertSortant.getTotal()));
        return this;
    }

    public RecapitulatifDepotAggregatStock calculerStockParUnite() {
        //initialStock + entreesAchat + entreesTransfert - sortiesVente - sortiesTransfert; by unite
        this.stockByUnite.put(uniteIntialStock, initialStock + this.stockByUnite.getOrDefault(uniteIntialStock, 0.0F));
        return this;
    }

    public Float getInitialStock() {
        return initialStock;
    }

    public Unite getUniteIntialStock() {
        return uniteIntialStock;
    }

    public List<StockDepot> getAchats() {
        return achats;
    }

    public List<StockDepot> getVentes() {
        return ventes;
    }

    public List<StockDepot> getTransfertsEntrants() {
        return transfertsEntrants;
    }

    public List<StockDepot> getTransfertsSortants() {
        return transfertsSortants;
    }

    public Map<Unite, Float> getStockByUnite() {
        return stockByUnite;
    }
}
