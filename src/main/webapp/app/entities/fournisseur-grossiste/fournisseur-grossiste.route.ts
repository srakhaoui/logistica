import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';
import { FournisseurGrossisteService } from './fournisseur-grossiste.service';
import { FournisseurGrossisteComponent } from './fournisseur-grossiste.component';
import { FournisseurGrossisteDetailComponent } from './fournisseur-grossiste-detail.component';
import { FournisseurGrossisteUpdateComponent } from './fournisseur-grossiste-update.component';
import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';

@Injectable({ providedIn: 'root' })
export class FournisseurGrossisteResolve implements Resolve<IFournisseurGrossiste> {
  constructor(private service: FournisseurGrossisteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFournisseurGrossiste> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((fournisseurGrossiste: HttpResponse<FournisseurGrossiste>) => fournisseurGrossiste.body));
    }
    return of(new FournisseurGrossiste());
  }
}

export const fournisseurGrossisteRoute: Routes = [
  {
    path: '',
    component: FournisseurGrossisteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseurGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FournisseurGrossisteDetailComponent,
    resolve: {
      fournisseurGrossiste: FournisseurGrossisteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseurGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FournisseurGrossisteUpdateComponent,
    resolve: {
      fournisseurGrossiste: FournisseurGrossisteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseurGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FournisseurGrossisteUpdateComponent,
    resolve: {
      fournisseurGrossiste: FournisseurGrossisteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.fournisseurGrossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
