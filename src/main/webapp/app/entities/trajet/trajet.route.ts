import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Trajet } from 'app/shared/model/trajet.model';
import { TrajetService } from './trajet.service';
import { TrajetComponent } from './trajet.component';
import { TrajetDetailComponent } from './trajet-detail.component';
import { TrajetUpdateComponent } from './trajet-update.component';
import { ITrajet } from 'app/shared/model/trajet.model';

@Injectable({ providedIn: 'root' })
export class TrajetResolve implements Resolve<ITrajet> {
  constructor(private service: TrajetService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrajet> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((trajet: HttpResponse<Trajet>) => trajet.body));
    }
    return of(new Trajet());
  }
}

export const trajetRoute: Routes = [
  {
    path: '',
    component: TrajetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.trajet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrajetDetailComponent,
    resolve: {
      trajet: TrajetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.trajet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrajetUpdateComponent,
    resolve: {
      trajet: TrajetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.trajet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrajetUpdateComponent,
    resolve: {
      trajet: TrajetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.trajet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
