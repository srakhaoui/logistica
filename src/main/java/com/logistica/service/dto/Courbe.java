package com.logistica.service.dto;

import java.util.ArrayList;
import java.util.List;

public class Courbe<X, Y> {
    private List<X> abscisses;
    private List<Y> ordonnees;

    public Courbe() {
        this.abscisses = new ArrayList<>();
        this.ordonnees = new ArrayList<>();
    }

    public List<X> getAbscisses() {
        return abscisses;
    }

    public List<Y> getOrdonnees() {
        return ordonnees;
    }
}
