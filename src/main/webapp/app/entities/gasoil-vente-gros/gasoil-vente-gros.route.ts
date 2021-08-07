import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';
import { GasoilVenteGrosService } from './gasoil-vente-gros.service';
import { GasoilVenteGrosComponent } from './gasoil-vente-gros.component';
import { GasoilVenteGrosDetailComponent } from './gasoil-vente-gros-detail.component';
import { GasoilVenteGrosUpdateComponent } from './gasoil-vente-gros-update.component';
import { IGasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';

@Injectable({ providedIn: 'root' })
export class GasoilVenteGrosResolve implements Resolve<IGasoilVenteGros> {
  constructor(private service: GasoilVenteGrosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGasoilVenteGros> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((gasoilVenteGros: HttpResponse<GasoilVenteGros>) => gasoilVenteGros.body));
    }
    return of(new GasoilVenteGros());
  }
}

export const gasoilVenteGrosRoute: Routes = [
  {
    path: '',
    component: GasoilVenteGrosComponent,
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilVenteGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GasoilVenteGrosDetailComponent,
    resolve: {
      gasoilVenteGros: GasoilVenteGrosResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilVenteGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GasoilVenteGrosUpdateComponent,
    resolve: {
      gasoilVenteGros: GasoilVenteGrosResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilVenteGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GasoilVenteGrosUpdateComponent,
    resolve: {
      gasoilVenteGros: GasoilVenteGrosResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilVenteGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
