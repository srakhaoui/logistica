import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITrajet } from 'app/shared/model/trajet.model';

type EntityResponseType = HttpResponse<ITrajet>;
type EntityArrayResponseType = HttpResponse<ITrajet[]>;

@Injectable({ providedIn: 'root' })
export class TrajetService {
  public resourceUrl = SERVER_API_URL + 'api/trajets';

  constructor(protected http: HttpClient) {}

  create(trajet: ITrajet): Observable<EntityResponseType> {
    return this.http.post<ITrajet>(this.resourceUrl, trajet, { observe: 'response' });
  }

  update(trajet: ITrajet): Observable<EntityResponseType> {
    return this.http.put<ITrajet>(this.resourceUrl, trajet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrajet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrajet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
