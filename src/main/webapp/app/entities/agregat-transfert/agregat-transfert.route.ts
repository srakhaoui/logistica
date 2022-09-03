import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AgregatTransfert } from 'app/shared/model/agregat-transfert.model';
import { AgregatTransfertService } from './agregat-transfert.service';
import { AgregatTransfertComponent } from './agregat-transfert.component';
import { AgregatTransfertDetailComponent } from './agregat-transfert-detail.component';
import { AgregatTransfertUpdateComponent } from './agregat-transfert-update.component';
import { IAgregatTransfert } from 'app/shared/model/agregat-transfert.model';

@Injectable({ providedIn: 'root' })
export class AgregatTransfertResolve implements Resolve<IAgregatTransfert> {
  constructor(private service: AgregatTransfertService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgregatTransfert> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((agregatTransfert: HttpResponse<AgregatTransfert>) => agregatTransfert.body));
    }
    return of(new AgregatTransfert());
  }
}

export const agregatTransfertRoute: Routes = [
  {
    path: '',
    component: AgregatTransfertComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.agregatTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgregatTransfertDetailComponent,
    resolve: {
      agregatTransfert: AgregatTransfertResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.agregatTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgregatTransfertUpdateComponent,
    resolve: {
      agregatTransfert: AgregatTransfertResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.agregatTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgregatTransfertUpdateComponent,
    resolve: {
      agregatTransfert: AgregatTransfertResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.agregatTransfert.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
