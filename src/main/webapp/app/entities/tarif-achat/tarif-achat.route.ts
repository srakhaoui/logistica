import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TarifAchat } from 'app/shared/model/tarif-achat.model';
import { TarifAchatService } from './tarif-achat.service';
import { TarifAchatComponent } from './tarif-achat.component';
import { TarifAchatDetailComponent } from './tarif-achat-detail.component';
import { TarifAchatUpdateComponent } from './tarif-achat-update.component';
import { ITarifAchat } from 'app/shared/model/tarif-achat.model';

@Injectable({ providedIn: 'root' })
export class TarifAchatResolve implements Resolve<ITarifAchat> {
  constructor(private service: TarifAchatService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifAchat> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((tarifAchat: HttpResponse<TarifAchat>) => tarifAchat.body));
    }
    return of(new TarifAchat());
  }
}

export const tarifAchatRoute: Routes = [
  {
    path: '',
    component: TarifAchatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifAchat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarifAchatDetailComponent,
    resolve: {
      tarifAchat: TarifAchatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifAchat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarifAchatUpdateComponent,
    resolve: {
      tarifAchat: TarifAchatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifAchat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarifAchatUpdateComponent,
    resolve: {
      tarifAchat: TarifAchatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.tarifAchat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
