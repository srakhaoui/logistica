import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'societe',
        loadChildren: () => import('./societe/societe.module').then(m => m.LogisticaSocieteModule)
      },
      {
        path: 'transporteur',
        loadChildren: () => import('./transporteur/transporteur.module').then(m => m.LogisticaTransporteurModule)
      },
      {
        path: 'fournisseur',
        loadChildren: () => import('./fournisseur/fournisseur.module').then(m => m.LogisticaFournisseurModule)
      },
      {
        path: 'produit',
        loadChildren: () => import('./produit/produit.module').then(m => m.LogisticaProduitModule)
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.LogisticaClientModule)
      },
      {
        path: 'trajet',
        loadChildren: () => import('./trajet/trajet.module').then(m => m.LogisticaTrajetModule)
      },
      {
        path: 'tarif-vente',
        loadChildren: () => import('./tarif-vente/tarif-vente.module').then(m => m.LogisticaTarifVenteModule)
      },
      {
        path: 'tarif-achat',
        loadChildren: () => import('./tarif-achat/tarif-achat.module').then(m => m.LogisticaTarifAchatModule)
      },
      {
        path: 'tarif-transport',
        loadChildren: () => import('./tarif-transport/tarif-transport.module').then(m => m.LogisticaTarifTransportModule)
      },
      {
        path: 'livraison',
        loadChildren: () => import('./livraison/livraison.module').then(m => m.LogisticaLivraisonModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LogisticaEntityModule {}
