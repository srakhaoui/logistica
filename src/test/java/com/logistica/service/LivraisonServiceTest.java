package com.logistica.service;

import com.logistica.service.dto.ChiffreAffaireParRepartition;
import com.logistica.service.dto.Courbe;
import com.logistica.service.impl.LivraisonServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class LivraisonServiceTest {

    private LivraisonService livraisonService = new LivraisonServiceImpl(null);

    @Test
    public void testGetCourbeWithRepartitionSizeLessThanMaxRepartitionSize() {
        //given
        int maxRepartitionsSize = 15;
        livraisonService.setMaxRepartitionSize(maxRepartitionsSize);
        List<ChiffreAffaireParRepartition> repartitions = getChiffreAffaireParRepartition(10);
        //when
        Courbe<String, Float> courbe = livraisonService.getCourbe(repartitions);
        //then
        assertThat(courbe.getAbscisses().size()).isEqualTo(10);
        Set<String> expectedRepartitions = repartitions.stream().map(ChiffreAffaireParRepartition::getElementRepartition).collect(Collectors.toSet());
        assertThat(new HashSet<>(courbe.getAbscisses())).containsExactlyElementsOf(expectedRepartitions);
        assertThat(courbe.getOrdonnees().size()).isEqualTo(10);
        Set<Float> chiffresAffaire = repartitions.stream().map(ChiffreAffaireParRepartition::getChiffreAffaire).map(Double::floatValue).collect(Collectors.toSet());
        assertThat(new HashSet<>(courbe.getOrdonnees())).containsExactlyElementsOf(chiffresAffaire);
    }

    @Test
    public void testGetCourbeWithRepartitionSizeGreaterThanMaxRepartitionSize() {
        //given
        int maxRepartitionsSize = 10;
        livraisonService.setMaxRepartitionSize(maxRepartitionsSize);
        List<ChiffreAffaireParRepartition> repartitions = getChiffreAffaireParRepartition(15);
        //when
        Courbe<String, Float> courbe = livraisonService.getCourbe(repartitions);
        //then
        assertThat(courbe.getAbscisses().size()).isEqualTo(maxRepartitionsSize);
        List<String> expectedRepartitions = repartitions.subList(0, maxRepartitionsSize - 1).stream().map(ChiffreAffaireParRepartition::getElementRepartition).collect(Collectors.toList());
        assertThat(new HashSet<>(courbe.getAbscisses())).containsAll(expectedRepartitions);
        assertThat(courbe.getAbscisses().get(maxRepartitionsSize - 1)).isEqualTo(LivraisonService.LE_RESTE);
        assertThat(courbe.getOrdonnees().size()).isEqualTo(maxRepartitionsSize);
        List<Float> chiffresAffaire = repartitions.subList(0, maxRepartitionsSize - 1).stream().map(ChiffreAffaireParRepartition::getChiffreAffaire).map(Double::floatValue).collect(Collectors.toList());
        assertThat(new HashSet<>(courbe.getOrdonnees())).containsAll(chiffresAffaire);
        assertThat(courbe.getOrdonnees().get(maxRepartitionsSize - 1)).isEqualTo(69);
    }

    private List<ChiffreAffaireParRepartition> getChiffreAffaireParRepartition(int size) {
        List<ChiffreAffaireParRepartition> repartitions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            repartitions.add(new ChiffreAffaireParRepartition(String.format("Repartitions%s", i), Double.valueOf(i)));
        }
        return repartitions;
    }
}
