import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';

type EntityResponseType = HttpResponse<IGasoilVenteGros>;
type EntityArrayResponseType = HttpResponse<IGasoilVenteGros[]>;

@Injectable({ providedIn: 'root' })
export class GasoilVenteGrosService {
  public resourceUrl = SERVER_API_URL + 'api/gasoil-vente-gros';

  constructor(protected http: HttpClient) {}

  create(gasoilVenteGros: IGasoilVenteGros): Observable<EntityResponseType> {
    return this.http.post<IGasoilVenteGros>(this.resourceUrl, gasoilVenteGros, { observe: 'response' });
  }

  update(gasoilVenteGros: IGasoilVenteGros): Observable<EntityResponseType> {
    return this.http.put<IGasoilVenteGros>(this.resourceUrl, gasoilVenteGros, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGasoilVenteGros>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGasoilVenteGros[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
