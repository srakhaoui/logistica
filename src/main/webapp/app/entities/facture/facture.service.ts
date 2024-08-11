import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IFacturationResponse } from 'app/shared/model/facturation-response.facture.model';
import { IFacturationRequest } from 'app/shared/model/facturation-request.facture.model';
import { IFacture } from 'app/shared/model/facture.model';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReglementEspeceRequest } from 'app/shared/model/paiement-espece-request.reglement.model';
import { IReglementEspeceResponse } from 'app/shared/model/paiement-espece-response.reglement.model';

type EntityArrayResponseType = HttpResponse<IFacture[]>;

@Injectable({ providedIn: 'root' })
export class FactureService {
  public resourceUrl = SERVER_API_URL + 'api/factures';
  public reglementUrl = SERVER_API_URL + 'api/reglements/espece';

  constructor(protected http: HttpClient) {}

  facturer(facturationRequest?: IFacturationRequest): Observable<IFacturationResponse> {
    return this.http.post<IFacturationResponse>(`${this.resourceUrl}`, facturationRequest, { observe: 'body' });
  }

  findFactures(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacture[]>(`${this.resourceUrl}`, { params: options, observe: 'response' });
  }

  payCash(iReglementEspeceRequest: IReglementEspeceRequest) {
    return this.http.post<IReglementEspeceResponse>(`${this.reglementUrl}`, iReglementEspeceRequest, { observe: 'body' });
  }
}
