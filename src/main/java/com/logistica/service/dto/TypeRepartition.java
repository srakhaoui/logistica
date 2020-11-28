package com.logistica.service.dto;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public enum TypeRepartition {
    SOCIETE_FACTURATION {
        @Override
        public Function<StatistiquesChiffreAffaire, Courbe<String, Float>> statsGetter() {
            return stats -> stats.getChiffreAffaireParSociete();
        }

        @Override
        public Consumer<StatistiquesChiffreAffaireRequest> statsSetter() {
            return statsRequest -> statsRequest.setWithRepartitionParSocieteFacturation(true);
        }

        @Override
        public String repartition() {
            return "societe-facturation";
        }
    },
    PRODUIT {
        @Override
        public Function<StatistiquesChiffreAffaire, Courbe<String, Float>> statsGetter() {
            return stats -> stats.getChiffreAffaireParProduit();
        }

        @Override
        public Consumer<StatistiquesChiffreAffaireRequest> statsSetter() {
            return statsRequest -> statsRequest.setWithRepartitionParProduit(true);
        }

        @Override
        public String repartition() {
            return "produit";
        }
    },
    TRAJET {
        @Override
        public Function<StatistiquesChiffreAffaire, Courbe<String, Float>> statsGetter() {
            return stats -> stats.getChiffreAffaireParTrajet();
        }

        @Override
        public Consumer<StatistiquesChiffreAffaireRequest> statsSetter() {
            return statsRequest -> statsRequest.setWithRepartitionParTrajet(true);
        }

        @Override
        public String repartition() {
            return "trajet";
        }
    },
    MATRICULE {
        @Override
        public Function<StatistiquesChiffreAffaire, Courbe<String, Float>> statsGetter() {
            return stats -> stats.getChiffreAffaireParMatricule();
        }

        @Override
        public Consumer<StatistiquesChiffreAffaireRequest> statsSetter() {
            return statsRequest -> statsRequest.setWithRepartitionParMatricule(true);
        }

        @Override
        public String repartition() {
            return "matricule";
        }
    },
    TYPE_LIVRAISON {
        @Override
        public Function<StatistiquesChiffreAffaire, Courbe<String, Float>> statsGetter() {
            return stats -> stats.getChiffreAffaireParType();
        }

        @Override
        public Consumer<StatistiquesChiffreAffaireRequest> statsSetter() {
            return statsRequest -> statsRequest.setWithRepartitionParTypeLivraison(true);
        }

        @Override
        public String repartition() {
            return "type-livraison";
        }
    },
    MOIS {
        @Override
        public Function<StatistiquesChiffreAffaire, Courbe<String, Float>> statsGetter() {
            return stats -> stats.getEvolution();
        }

        @Override
        public Consumer<StatistiquesChiffreAffaireRequest> statsSetter() {
            return statsRequest -> statsRequest.setWithEvolutionChiffreAffaire(true);
        }

        @Override
        public String repartition() {
            return "mois";
        }
    };

    public abstract Function<StatistiquesChiffreAffaire, Courbe<String, Float>> statsGetter();

    public abstract Consumer<StatistiquesChiffreAffaireRequest> statsSetter();

    public abstract String repartition();

    public static TypeRepartition fromValue(String reparition) {
        return Arrays.stream(TypeRepartition.values())
            .filter(typeRepartition -> typeRepartition.repartition().equalsIgnoreCase(reparition))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported repartition type %s", reparition)));
    }
}
