import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Livraison } from 'app/shared/model/livraison.model';
import { LivraisonService } from './livraison.service';
import { LivraisonComponent } from './livraison.component';
import { LivraisonDetailComponent } from './livraison-detail.component';
import { LivraisonUpdateComponent } from './livraison-update.component';
import { ILivraison } from 'app/shared/model/livraison.model';

@Injectable({ providedIn: 'root' })
export class LivraisonResolve implements Resolve<ILivraison> {
  constructor(private service: LivraisonService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivraison> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((livraison: HttpResponse<Livraison>) => livraison.body));
    }
    return of(new Livraison());
  }
}

export const livraisonRoute: Routes = [
  {
    path: '',
    component: LivraisonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LivraisonDetailComponent,
    resolve: {
      livraison: LivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LivraisonUpdateComponent,
    resolve: {
      livraison: LivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LivraisonUpdateComponent,
    resolve: {
      livraison: LivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
