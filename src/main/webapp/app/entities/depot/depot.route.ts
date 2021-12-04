import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Depot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';
import { DepotComponent } from './depot.component';
import { DepotDetailComponent } from './depot-detail.component';
import { DepotUpdateComponent } from './depot-update.component';
import { IDepot } from 'app/shared/model/depot.model';

@Injectable({ providedIn: 'root' })
export class DepotResolve implements Resolve<IDepot> {
  constructor(private service: DepotService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepot> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((depot: HttpResponse<Depot>) => depot.body));
    }
    return of(new Depot());
  }
}

export const depotRoute: Routes = [
  {
    path: '',
    component: DepotComponent,
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.depot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepotDetailComponent,
    resolve: {
      depot: DepotResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.depot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepotUpdateComponent,
    resolve: {
      depot: DepotResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.depot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepotUpdateComponent,
    resolve: {
      depot: DepotResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.depot.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
