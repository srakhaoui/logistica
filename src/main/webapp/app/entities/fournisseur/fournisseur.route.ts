import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Fournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from './fournisseur.service';
import { FournisseurComponent } from './fournisseur.component';
import { FournisseurDetailComponent } from './fournisseur-detail.component';
import { FournisseurUpdateComponent } from './fournisseur-update.component';
import { IFournisseur } from 'app/shared/model/fournisseur.model';

@Injectable({ providedIn: 'root' })
export class FournisseurResolve implements Resolve<IFournisseur> {
  constructor(private service: FournisseurService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFournisseur> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((fournisseur: HttpResponse<Fournisseur>) => fournisseur.body));
    }
    return of(new Fournisseur());
  }
}

export const fournisseurRoute: Routes = [
  {
    path: '',
    component: FournisseurComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FournisseurDetailComponent,
    resolve: {
      fournisseur: FournisseurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FournisseurUpdateComponent,
    resolve: {
      fournisseur: FournisseurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FournisseurUpdateComponent,
    resolve: {
      fournisseur: FournisseurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseur.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
