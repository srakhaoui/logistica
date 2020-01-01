import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Transporteur } from 'app/shared/model/transporteur.model';
import { TransporteurService } from './transporteur.service';
import { TransporteurComponent } from './transporteur.component';
import { TransporteurDetailComponent } from './transporteur-detail.component';
import { TransporteurUpdateComponent } from './transporteur-update.component';
import { ITransporteur } from 'app/shared/model/transporteur.model';

@Injectable({ providedIn: 'root' })
export class TransporteurResolve implements Resolve<ITransporteur> {
  constructor(private service: TransporteurService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransporteur> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((transporteur: HttpResponse<Transporteur>) => transporteur.body));
    }
    return of(new Transporteur());
  }
}

export const transporteurRoute: Routes = [
  {
    path: '',
    component: TransporteurComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.transporteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransporteurDetailComponent,
    resolve: {
      transporteur: TransporteurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.transporteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransporteurUpdateComponent,
    resolve: {
      transporteur: TransporteurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.transporteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransporteurUpdateComponent,
    resolve: {
      transporteur: TransporteurResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.transporteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
