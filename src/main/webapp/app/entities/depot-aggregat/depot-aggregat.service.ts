import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepotAggregat } from 'app/shared/model/depot-aggregat.model';

type EntityResponseType = HttpResponse<IDepotAggregat>;
type EntityArrayResponseType = HttpResponse<IDepotAggregat[]>;

@Injectable({ providedIn: 'root' })
export class DepotAggregatService {
  public resourceUrl = SERVER_API_URL + 'api/depot-aggregats';

  constructor(protected http: HttpClient) {}

  create(depotAggregat: IDepotAggregat): Observable<EntityResponseType> {
    return this.http.post<IDepotAggregat>(this.resourceUrl, depotAggregat, { observe: 'response' });
  }

  update(depotAggregat: IDepotAggregat): Observable<EntityResponseType> {
    return this.http.put<IDepotAggregat>(this.resourceUrl, depotAggregat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepotAggregat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepotAggregat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
