package com.logistica.domain.enumeration;

import com.logistica.domain.Livraison;

/**
 * The Unite enumeration.
 */
public enum Unite {
    Tonne {
        @Override
        public Float getPrixTotalVente(Livraison livraison, Float prixUnitaire) {
            return livraison.getQuantiteVendue() * prixUnitaire;
        }

        @Override
        public Float getPrixTotalAchat(Livraison livraison, Float prixUnitaire) {
            return livraison.getQuantiteAchetee() * prixUnitaire;
        }
    }, M3 {
        @Override
        public Float getPrixTotalVente(Livraison livraison, Float prixUnitaire) {
            return livraison.getQuantiteVendue() * prixUnitaire;
        }

        @Override
        public Float getPrixTotalAchat(Livraison livraison, Float prixUnitaire) {
            return livraison.getQuantiteAchetee() * prixUnitaire;
        }
    }, Voyage {
        @Override
        public Float getPrixTotalVente(Livraison livraison, Float prixUnitaire) {
            return prixUnitaire;
        }

        @Override
        public Float getPrixTotalAchat(Livraison livraison, Float prixUnitaire) {
            return prixUnitaire;
        }
    };

    public abstract Float getPrixTotalVente(Livraison livraison, Float prixUnitaire);

    public abstract Float getPrixTotalAchat(Livraison livraison, Float prixUnitaire);
}
