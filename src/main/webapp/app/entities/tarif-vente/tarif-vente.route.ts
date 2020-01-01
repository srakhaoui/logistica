import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TarifVente } from 'app/shared/model/tarif-vente.model';
import { TarifVenteService } from './tarif-vente.service';
import { TarifVenteComponent } from './tarif-vente.component';
import { TarifVenteDetailComponent } from './tarif-vente-detail.component';
import { TarifVenteUpdateComponent } from './tarif-vente-update.component';
import { ITarifVente } from 'app/shared/model/tarif-vente.model';

@Injectable({ providedIn: 'root' })
export class TarifVenteResolve implements Resolve<ITarifVente> {
  constructor(private service: TarifVenteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifVente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((tarifVente: HttpResponse<TarifVente>) => tarifVente.body));
    }
    return of(new TarifVente());
  }
}

export const tarifVenteRoute: Routes = [
  {
    path: '',
    component: TarifVenteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarifVenteDetailComponent,
    resolve: {
      tarifVente: TarifVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarifVenteUpdateComponent,
    resolve: {
      tarifVente: TarifVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarifVenteUpdateComponent,
    resolve: {
      tarifVente: TarifVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
