import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Gasoil } from 'app/shared/model/gasoil.model';
import { GasoilService } from './gasoil.service';
import { GasoilComponent } from './gasoil.component';
import { GasoilDetailComponent } from './gasoil-detail.component';
import { GasoilUpdateComponent } from './gasoil-update.component';
import { IGasoil } from 'app/shared/model/gasoil.model';

@Injectable({ providedIn: 'root' })
export class GasoilResolve implements Resolve<IGasoil> {
  constructor(private service: GasoilService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGasoil> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((gasoil: HttpResponse<Gasoil>) => gasoil.body));
    }
    return of(new Gasoil());
  }
}

export const gasoilRoute: Routes = [
  {
    path: '',
    component: GasoilComponent,
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GasoilDetailComponent,
    resolve: {
      gasoil: GasoilResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GasoilUpdateComponent,
    resolve: {
      gasoil: GasoilResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GasoilUpdateComponent,
    resolve: {
      gasoil: GasoilResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.gasoil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
