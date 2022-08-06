import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DepotAggregat } from 'app/shared/model/depot-aggregat.model';
import { DepotAggregatService } from './depot-aggregat.service';
import { DepotAggregatComponent } from './depot-aggregat.component';
import { DepotAggregatDetailComponent } from './depot-aggregat-detail.component';
import { DepotAggregatUpdateComponent } from './depot-aggregat-update.component';
import { IDepotAggregat } from 'app/shared/model/depot-aggregat.model';

@Injectable({ providedIn: 'root' })
export class DepotAggregatResolve implements Resolve<IDepotAggregat> {
  constructor(private service: DepotAggregatService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepotAggregat> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((depotAggregat: HttpResponse<DepotAggregat>) => depotAggregat.body));
    }
    return of(new DepotAggregat());
  }
}

export const depotAggregatRoute: Routes = [
  {
    path: '',
    component: DepotAggregatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.depotAggregat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepotAggregatDetailComponent,
    resolve: {
      depotAggregat: DepotAggregatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.depotAggregat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepotAggregatUpdateComponent,
    resolve: {
      depotAggregat: DepotAggregatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.depotAggregat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepotAggregatUpdateComponent,
    resolve: {
      depotAggregat: DepotAggregatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.depotAggregat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
