import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GasoilTransfert } from 'app/shared/model/gasoil-transfert.model';
import { GasoilTransfertService } from './gasoil-transfert.service';
import { GasoilTransfertComponent } from './gasoil-transfert.component';
import { GasoilTransfertDetailComponent } from './gasoil-transfert-detail.component';
import { GasoilTransfertUpdateComponent } from './gasoil-transfert-update.component';
import { IGasoilTransfert } from 'app/shared/model/gasoil-transfert.model';

@Injectable({ providedIn: 'root' })
export class GasoilTransfertResolve implements Resolve<IGasoilTransfert> {
  constructor(private service: GasoilTransfertService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGasoilTransfert> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((gasoilTransfert: HttpResponse<GasoilTransfert>) => gasoilTransfert.body));
    }
    return of(new GasoilTransfert());
  }
}

export const gasoilTransfertRoute: Routes = [
  {
    path: '',
    component: GasoilTransfertComponent,
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GasoilTransfertDetailComponent,
    resolve: {
      gasoilTransfert: GasoilTransfertResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GasoilTransfertUpdateComponent,
    resolve: {
      gasoilTransfert: GasoilTransfertResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GasoilTransfertUpdateComponent,
    resolve: {
      gasoilTransfert: GasoilTransfertResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
