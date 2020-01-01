import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifTransport } from 'app/shared/model/tarif-transport.model';

type EntityResponseType = HttpResponse<ITarifTransport>;
type EntityArrayResponseType = HttpResponse<ITarifTransport[]>;

@Injectable({ providedIn: 'root' })
export class TarifTransportService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-transports';

  constructor(protected http: HttpClient) {}

  create(tarifTransport: ITarifTransport): Observable<EntityResponseType> {
    return this.http.post<ITarifTransport>(this.resourceUrl, tarifTransport, { observe: 'response' });
  }

  update(tarifTransport: ITarifTransport): Observable<EntityResponseType> {
    return this.http.put<ITarifTransport>(this.resourceUrl, tarifTransport, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifTransport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifTransport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
