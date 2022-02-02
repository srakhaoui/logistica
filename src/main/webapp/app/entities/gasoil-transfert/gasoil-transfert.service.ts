import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGasoilTransfert } from 'app/shared/model/gasoil-transfert.model';

type EntityResponseType = HttpResponse<IGasoilTransfert>;
type EntityArrayResponseType = HttpResponse<IGasoilTransfert[]>;

@Injectable({ providedIn: 'root' })
export class GasoilTransfertService {
  public resourceUrl = SERVER_API_URL + 'api/gasoil-transferts';

  constructor(protected http: HttpClient) {}

  create(gasoilTransfert: IGasoilTransfert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gasoilTransfert);
    return this.http
      .post<IGasoilTransfert>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(gasoilTransfert: IGasoilTransfert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gasoilTransfert);
    return this.http
      .put<IGasoilTransfert>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGasoilTransfert>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGasoilTransfert[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(gasoilTransfert: IGasoilTransfert): IGasoilTransfert {
    const copy: IGasoilTransfert = Object.assign({}, gasoilTransfert, {
      transfertDate:
        gasoilTransfert.transfertDate != null && gasoilTransfert.transfertDate.isValid()
          ? gasoilTransfert.transfertDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transfertDate = res.body.transfertDate != null ? moment(res.body.transfertDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((gasoilTransfert: IGasoilTransfert) => {
        gasoilTransfert.transfertDate = gasoilTransfert.transfertDate != null ? moment(gasoilTransfert.transfertDate) : null;
      });
    }
    return res;
  }
}
