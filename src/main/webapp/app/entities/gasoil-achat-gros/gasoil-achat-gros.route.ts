import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';
import { GasoilAchatGrosService } from './gasoil-achat-gros.service';
import { GasoilAchatGrosComponent } from './gasoil-achat-gros.component';
import { GasoilAchatGrosDetailComponent } from './gasoil-achat-gros-detail.component';
import { GasoilAchatGrosUpdateComponent } from './gasoil-achat-gros-update.component';
import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

@Injectable({ providedIn: 'root' })
export class GasoilAchatGrosResolve implements Resolve<IGasoilAchatGros> {
  constructor(private service: GasoilAchatGrosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGasoilAchatGros> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((gasoilAchatGros: HttpResponse<GasoilAchatGros>) => gasoilAchatGros.body));
    }
    return of(new GasoilAchatGros());
  }
}

export const gasoilAchatGrosRoute: Routes = [
  {
    path: '',
    component: GasoilAchatGrosComponent,
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilAchatGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GasoilAchatGrosDetailComponent,
    resolve: {
      gasoilAchatGros: GasoilAchatGrosResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilAchatGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GasoilAchatGrosUpdateComponent,
    resolve: {
      gasoilAchatGros: GasoilAchatGrosResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilAchatGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GasoilAchatGrosUpdateComponent,
    resolve: {
      gasoilAchatGros: GasoilAchatGrosResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoilAchatGros.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
