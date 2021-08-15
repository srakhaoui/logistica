import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Carburant } from 'app/shared/model/carburant.model';
import { CarburantService } from './carburant.service';
import { CarburantComponent } from './carburant.component';
import { CarburantDetailComponent } from './carburant-detail.component';
import { CarburantUpdateComponent } from './carburant-update.component';
import { ICarburant } from 'app/shared/model/carburant.model';

@Injectable({ providedIn: 'root' })
export class CarburantResolve implements Resolve<ICarburant> {
  constructor(private service: CarburantService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarburant> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((carburant: HttpResponse<Carburant>) => carburant.body));
    }
    return of(new Carburant());
  }
}

export const carburantRoute: Routes = [
  {
    path: '',
    component: CarburantComponent,
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.carburant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarburantDetailComponent,
    resolve: {
      carburant: CarburantResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.carburant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarburantUpdateComponent,
    resolve: {
      carburant: CarburantResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.carburant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarburantUpdateComponent,
    resolve: {
      carburant: CarburantResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.carburant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
