import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Societe } from 'app/shared/model/societe.model';
import { SocieteService } from './societe.service';
import { SocieteComponent } from './societe.component';
import { SocieteDetailComponent } from './societe-detail.component';
import { SocieteUpdateComponent } from './societe-update.component';
import { ISociete } from 'app/shared/model/societe.model';

@Injectable({ providedIn: 'root' })
export class SocieteResolve implements Resolve<ISociete> {
  constructor(private service: SocieteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISociete> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((societe: HttpResponse<Societe>) => societe.body));
    }
    return of(new Societe());
  }
}

export const societeRoute: Routes = [
  {
    path: '',
    component: SocieteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.societe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SocieteDetailComponent,
    resolve: {
      societe: SocieteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.societe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SocieteUpdateComponent,
    resolve: {
      societe: SocieteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.societe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SocieteUpdateComponent,
    resolve: {
      societe: SocieteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.societe.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
