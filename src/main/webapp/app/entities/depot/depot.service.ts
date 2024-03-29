import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepot } from 'app/shared/model/depot.model';

type EntityResponseType = HttpResponse<IDepot>;
type EntityArrayResponseType = HttpResponse<IDepot[]>;
type DoubleResponseType = HttpResponse<number>;

@Injectable({ providedIn: 'root' })
export class DepotService {
  public resourceUrl = SERVER_API_URL + 'api/depots';

  constructor(protected http: HttpClient) {}

  create(depot: IDepot): Observable<EntityResponseType> {
    return this.http.post<IDepot>(this.resourceUrl, depot, { observe: 'response' });
  }

  update(depot: IDepot): Observable<EntityResponseType> {
    return this.http.put<IDepot>(this.resourceUrl, depot, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStock(nomDepot: string): Observable<DoubleResponseType> {
    return this.http.get<number>(`${this.resourceUrl}/stocks/${nomDepot}`, {observe: 'response' });
  }
}
