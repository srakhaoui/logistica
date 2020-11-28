import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISociete } from 'app/shared/model/societe.model';

type EntityResponseType = HttpResponse<ISociete>;
type EntityArrayResponseType = HttpResponse<ISociete[]>;

@Injectable({ providedIn: 'root' })
export class SocieteService {
  public resourceUrl = SERVER_API_URL + 'api/societes';

  constructor(protected http: HttpClient) {}

  create(societe: ISociete): Observable<EntityResponseType> {
    return this.http.post<ISociete>(this.resourceUrl, societe, { observe: 'response' });
  }

  update(societe: ISociete): Observable<EntityResponseType> {
    return this.http.put<ISociete>(this.resourceUrl, societe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISociete>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISociete[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
