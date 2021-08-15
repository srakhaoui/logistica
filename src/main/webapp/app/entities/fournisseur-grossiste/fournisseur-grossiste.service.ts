import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';

type EntityResponseType = HttpResponse<IFournisseurGrossiste>;
type EntityArrayResponseType = HttpResponse<IFournisseurGrossiste[]>;

@Injectable({ providedIn: 'root' })
export class FournisseurGrossisteService {
  public resourceUrl = SERVER_API_URL + 'api/fournisseur-grossistes';

  constructor(protected http: HttpClient) {}

  create(fournisseurGrossiste: IFournisseurGrossiste): Observable<EntityResponseType> {
    return this.http.post<IFournisseurGrossiste>(this.resourceUrl, fournisseurGrossiste, { observe: 'response' });
  }

  update(fournisseurGrossiste: IFournisseurGrossiste): Observable<EntityResponseType> {
    return this.http.put<IFournisseurGrossiste>(this.resourceUrl, fournisseurGrossiste, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFournisseurGrossiste>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFournisseurGrossiste[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
