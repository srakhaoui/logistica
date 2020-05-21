package com.logistica.domain.enumeration;


import com.logistica.domain.Livraison;

public enum TypeBon {
    Commande {
        @Override
        public byte[] bonContent(Livraison livraison) {
            return livraison.getBonCommande();
        }

        @Override
        public String bonContentType(Livraison livraison) {
            return livraison.getBonCommandeMimeType();
        }
    },
    Livraison {
        @Override
        public byte[] bonContent(Livraison livraison) {
            return livraison.getBonLivraison();
        }

        @Override
        public String bonContentType(Livraison livraison) {
            return livraison.getBonLivraisonMimeType();
        }
    },
    Fournisseur {
        @Override
        public byte[] bonContent(Livraison livraison) {
            return livraison.getBonFournisseur();
        }

        @Override
        public String bonContentType(Livraison livraison) {
            return livraison.getBonFournisseurMimeType();
        }
    };

    abstract public byte[] bonContent(Livraison livraison);

    abstract public String bonContentType(Livraison livraison);
}
