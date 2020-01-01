import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { ProduitComponent } from './produit.component';
import { ProduitDetailComponent } from './produit-detail.component';
import { ProduitUpdateComponent } from './produit-update.component';
import { IProduit } from 'app/shared/model/produit.model';

@Injectable({ providedIn: 'root' })
export class ProduitResolve implements Resolve<IProduit> {
  constructor(private service: ProduitService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProduit> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((produit: HttpResponse<Produit>) => produit.body));
    }
    return of(new Produit());
  }
}

export const produitRoute: Routes = [
  {
    path: '',
    component: ProduitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.produit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProduitDetailComponent,
    resolve: {
      produit: ProduitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.produit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProduitUpdateComponent,
    resolve: {
      produit: ProduitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.produit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProduitUpdateComponent,
    resolve: {
      produit: ProduitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.produit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
