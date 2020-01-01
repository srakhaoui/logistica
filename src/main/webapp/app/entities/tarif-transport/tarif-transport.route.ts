import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TarifTransport } from 'app/shared/model/tarif-transport.model';
import { TarifTransportService } from './tarif-transport.service';
import { TarifTransportComponent } from './tarif-transport.component';
import { TarifTransportDetailComponent } from './tarif-transport-detail.component';
import { TarifTransportUpdateComponent } from './tarif-transport-update.component';
import { ITarifTransport } from 'app/shared/model/tarif-transport.model';

@Injectable({ providedIn: 'root' })
export class TarifTransportResolve implements Resolve<ITarifTransport> {
  constructor(private service: TarifTransportService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifTransport> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((tarifTransport: HttpResponse<TarifTransport>) => tarifTransport.body));
    }
    return of(new TarifTransport());
  }
}

export const tarifTransportRoute: Routes = [
  {
    path: '',
    component: TarifTransportComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarifTransportDetailComponent,
    resolve: {
      tarifTransport: TarifTransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarifTransportUpdateComponent,
    resolve: {
      tarifTransport: TarifTransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarifTransportUpdateComponent,
    resolve: {
      tarifTransport: TarifTransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
