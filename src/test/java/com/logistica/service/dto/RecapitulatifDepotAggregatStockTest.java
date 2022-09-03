package com.logistica.service.dto;


import com.logistica.domain.enumeration.Unite;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecapitulatifDepotAggregatStockTest {

    @Test
    public void testCalculerStock() {
        //given
        float intialStock = 25.55F;
        List<StockDepot> achats = achats();
        List<StockDepot> ventes = ventes();
        List<StockDepot> transfertsEntrants = transfertsEntrants();
        List<StockDepot> transfertsSortants = transfertsSortants();
        //when
        RecapitulatifDepotAggregatStock recapitulatifDepotAggregatStock = new RecapitulatifDepotAggregatStock("depot", intialStock, Unite.Tonne)
            .achats(achats)
            .ventes(ventes)
            .transfertsEntrants(transfertsEntrants)
            .transfertsSortants(transfertsSortants)
            .calculerStockParUnite();
        //then
        assertThat(recapitulatifDepotAggregatStock.getStockByUnite())
            .containsOnly(MapEntry.entry(Unite.Tonne, 45.55F), MapEntry.entry(Unite.M3, 55.00F));
    }

    private List<StockDepot> achats() {
        return Arrays.asList(new StockDepot("depot", Unite.Tonne, 55.90F), new StockDepot("depot", Unite.M3, 80.90F));
    }

    private List<StockDepot> ventes() {
        return Arrays.asList(new StockDepot("depot", Unite.Tonne, 40.90F), new StockDepot("depot", Unite.M3, 30.90F));
    }

    private List<StockDepot> transfertsEntrants() {
        return Arrays.asList(new StockDepot("depot", Unite.Tonne, 10.90F), new StockDepot("depot", Unite.M3, 10.90F));
    }

    private List<StockDepot> transfertsSortants() {
        return Arrays.asList(new StockDepot("depot", Unite.Tonne, 5.90F), new StockDepot("depot", Unite.M3, 5.90F));
    }


}
