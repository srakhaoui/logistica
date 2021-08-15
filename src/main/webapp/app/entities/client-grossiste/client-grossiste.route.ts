import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { ClientGrossisteService } from './client-grossiste.service';
import { ClientGrossisteComponent } from './client-grossiste.component';
import { ClientGrossisteDetailComponent } from './client-grossiste-detail.component';
import { ClientGrossisteUpdateComponent } from './client-grossiste-update.component';
import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';

@Injectable({ providedIn: 'root' })
export class ClientGrossisteResolve implements Resolve<IClientGrossiste> {
  constructor(private service: ClientGrossisteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClientGrossiste> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((clientGrossiste: HttpResponse<ClientGrossiste>) => clientGrossiste.body));
    }
    return of(new ClientGrossiste());
  }
}

export const clientGrossisteRoute: Routes = [
  {
    path: '',
    component: ClientGrossisteComponent,
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.clientGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClientGrossisteDetailComponent,
    resolve: {
      clientGrossiste: ClientGrossisteResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.clientGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClientGrossisteUpdateComponent,
    resolve: {
      clientGrossiste: ClientGrossisteResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.clientGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClientGrossisteUpdateComponent,
    resolve: {
      clientGrossiste: ClientGrossisteResolve
    },
    data: {
      authorities: ['ROLE_GASOIL'],
      pageTitle: 'logisticaApp.clientGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
