import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransporteur } from 'app/shared/model/transporteur.model';

type EntityResponseType = HttpResponse<ITransporteur>;
type EntityArrayResponseType = HttpResponse<ITransporteur[]>;

@Injectable({ providedIn: 'root' })
export class TransporteurService {
  public resourceUrl = SERVER_API_URL + 'api/transporteurs';

  constructor(protected http: HttpClient) {}

  create(transporteur: ITransporteur): Observable<EntityResponseType> {
    return this.http.post<ITransporteur>(this.resourceUrl, transporteur, { observe: 'response' });
  }

  update(transporteur: ITransporteur): Observable<EntityResponseType> {
    return this.http.put<ITransporteur>(this.resourceUrl, transporteur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransporteur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransporteur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
