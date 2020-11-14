package com.logistica.service.dto;

public enum UniteRepartition {
    Trajet {
        @Override
        public String getter() {
            return "trajet.description";
        }
    },
    Produit {
        @Override
        public String getter() {
            return "produit.code";
        }
    },
    SocieteFacturation {
        @Override
        public String getter() {
            return "societeFacturation.nom";
        }
    },
    TypeLivraison {
        @Override
        public String getter() {
            return "type";
        }
    },
    Matricule {
        @Override
        public String getter() {
            return "transporteur.matricule";
        }
    };

    abstract public String getter();
}
