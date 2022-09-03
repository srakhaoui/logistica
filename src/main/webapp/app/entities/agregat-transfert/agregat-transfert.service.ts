import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAgregatTransfert } from 'app/shared/model/agregat-transfert.model';

type EntityResponseType = HttpResponse<IAgregatTransfert>;
type EntityArrayResponseType = HttpResponse<IAgregatTransfert[]>;

@Injectable({ providedIn: 'root' })
export class AgregatTransfertService {
  public resourceUrl = SERVER_API_URL + 'api/agregat-transferts';

  constructor(protected http: HttpClient) {}

  create(agregatTransfert: IAgregatTransfert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agregatTransfert);
    return this.http
      .post<IAgregatTransfert>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(agregatTransfert: IAgregatTransfert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agregatTransfert);
    return this.http
      .put<IAgregatTransfert>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAgregatTransfert>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAgregatTransfert[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(agregatTransfert: IAgregatTransfert): IAgregatTransfert {
    const copy: IAgregatTransfert = Object.assign({}, agregatTransfert, {
      transfertDate:
        agregatTransfert.transfertDate != null && agregatTransfert.transfertDate.isValid()
          ? agregatTransfert.transfertDate.format(DATE_FORMAT)
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
      res.body.forEach((agregatTransfert: IAgregatTransfert) => {
        agregatTransfert.transfertDate = agregatTransfert.transfertDate != null ? moment(agregatTransfert.transfertDate) : null;
      });
    }
    return res;
  }
}
