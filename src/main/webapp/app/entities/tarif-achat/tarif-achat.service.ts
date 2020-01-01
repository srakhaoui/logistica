import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifAchat } from 'app/shared/model/tarif-achat.model';

type EntityResponseType = HttpResponse<ITarifAchat>;
type EntityArrayResponseType = HttpResponse<ITarifAchat[]>;

@Injectable({ providedIn: 'root' })
export class TarifAchatService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-achats';

  constructor(protected http: HttpClient) {}

  create(tarifAchat: ITarifAchat): Observable<EntityResponseType> {
    return this.http.post<ITarifAchat>(this.resourceUrl, tarifAchat, { observe: 'response' });
  }

  update(tarifAchat: ITarifAchat): Observable<EntityResponseType> {
    return this.http.put<ITarifAchat>(this.resourceUrl, tarifAchat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifAchat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifAchat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
