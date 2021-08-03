import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

type EntityResponseType = HttpResponse<IGasoilAchatGros>;
type EntityArrayResponseType = HttpResponse<IGasoilAchatGros[]>;

@Injectable({ providedIn: 'root' })
export class GasoilAchatGrosService {
  public resourceUrl = SERVER_API_URL + 'api/gasoil-achat-gros';

  constructor(protected http: HttpClient) {}

  create(gasoilAchatGros: IGasoilAchatGros): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gasoilAchatGros);
    return this.http
      .post<IGasoilAchatGros>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(gasoilAchatGros: IGasoilAchatGros): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gasoilAchatGros);
    return this.http
      .put<IGasoilAchatGros>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGasoilAchatGros>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGasoilAchatGros[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(gasoilAchatGros: IGasoilAchatGros): IGasoilAchatGros {
    const copy: IGasoilAchatGros = Object.assign({}, gasoilAchatGros, {
      dateReception:
        gasoilAchatGros.dateReception != null && gasoilAchatGros.dateReception.isValid()
          ? gasoilAchatGros.dateReception.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateReception = res.body.dateReception != null ? moment(res.body.dateReception) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((gasoilAchatGros: IGasoilAchatGros) => {
        gasoilAchatGros.dateReception = gasoilAchatGros.dateReception != null ? moment(gasoilAchatGros.dateReception) : null;
      });
    }
    return res;
  }
}
