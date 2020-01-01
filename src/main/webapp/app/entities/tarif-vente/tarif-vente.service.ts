import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifVente } from 'app/shared/model/tarif-vente.model';

type EntityResponseType = HttpResponse<ITarifVente>;
type EntityArrayResponseType = HttpResponse<ITarifVente[]>;

@Injectable({ providedIn: 'root' })
export class TarifVenteService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-ventes';

  constructor(protected http: HttpClient) {}

  create(tarifVente: ITarifVente): Observable<EntityResponseType> {
    return this.http.post<ITarifVente>(this.resourceUrl, tarifVente, { observe: 'response' });
  }

  update(tarifVente: ITarifVente): Observable<EntityResponseType> {
    return this.http.put<ITarifVente>(this.resourceUrl, tarifVente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifVente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifVente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
