import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGasoil } from 'app/shared/model/gasoil.model';

type EntityResponseType = HttpResponse<IGasoil>;
type EntityArrayResponseType = HttpResponse<IGasoil[]>;
type IntegerResponseType = HttpResponse<number>;

@Injectable({ providedIn: 'root' })
export class GasoilService {
  public resourceUrl = SERVER_API_URL + 'api/gasoils';

  constructor(protected http: HttpClient) {}

  create(gasoil: IGasoil): Observable<EntityResponseType> {
    return this.http.post<IGasoil>(this.resourceUrl, gasoil, { observe: 'response' });
  }

  update(gasoil: IGasoil): Observable<EntityResponseType> {
    return this.http.put<IGasoil>(this.resourceUrl, gasoil, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGasoil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGasoil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getKilometrageFinal(req?: any): Observable<IntegerResponseType> {
    const options = createRequestOption(req);
    return this.http.get<number>(`${this.resourceUrl}/kilometrage/final`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
