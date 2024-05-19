import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IFacturationFactureResponse } from 'app/shared/model/facturation-response.facture.model';
import { IFacturationRequest } from 'app/shared/model/facturation-request.facture.model';

@Injectable({ providedIn: 'root' })
export class FactureService {
  public resourceUrl = SERVER_API_URL + 'api/factures';

  constructor(protected http: HttpClient) {}

  facturer(facturationRequest?: IFacturationRequest): Observable<IFacturationFactureResponse> {
    return this.http.post<IFacturationFactureResponse>(`${this.resourceUrl}`, facturationRequest, { observe: 'body' });
  }
}
