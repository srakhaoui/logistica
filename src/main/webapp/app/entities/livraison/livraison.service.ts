import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILivraison } from 'app/shared/model/livraison.model';

type EntityResponseType = HttpResponse<ILivraison>;
type EntityArrayResponseType = HttpResponse<ILivraison[]>;

@Injectable({ providedIn: 'root' })
export class LivraisonService {
  public resourceUrl = SERVER_API_URL + 'api/livraisons';

  constructor(protected http: HttpClient) {}

  create(livraison: ILivraison): Observable<EntityResponseType> {
    const formData = new FormData();
    formData.append('bon_commande', livraison.bonCommande);
    formData.append('bon_livraison', livraison.bonLivraison);
    formData.append('livraison', JSON.stringify(this.convertDateFromClient(livraison)));
    return this.http
      .post<ILivraison>(this.resourceUrl, formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(livraison: ILivraison): Observable<EntityResponseType> {
    const formData = new FormData();
    formData.append('bon_commande', livraison.bonCommande);
    formData.append('bon_livraison', livraison.bonLivraison);
    formData.append('livraison', JSON.stringify(this.convertDateFromClient(livraison)));
    return this.http
      .put<ILivraison>(this.resourceUrl, formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILivraison>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraison[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(livraison: ILivraison): ILivraison {
    const copy: ILivraison = Object.assign({}, livraison, {
      dateBonCommande:
        livraison.dateBonCommande != null && livraison.dateBonCommande.isValid() ? livraison.dateBonCommande.format(DATE_FORMAT) : null,
      dateBonLivraison:
        livraison.dateBonLivraison != null && livraison.dateBonLivraison.isValid() ? livraison.dateBonLivraison.format(DATE_FORMAT) : null,
      dateBonCaisse:
        livraison.dateBonCaisse != null && livraison.dateBonCaisse.isValid() ? livraison.dateBonCaisse.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateBonCommande = res.body.dateBonCommande != null ? moment(res.body.dateBonCommande) : null;
      res.body.dateBonLivraison = res.body.dateBonLivraison != null ? moment(res.body.dateBonLivraison) : null;
      res.body.dateBonCaisse = res.body.dateBonCaisse != null ? moment(res.body.dateBonCaisse) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((livraison: ILivraison) => {
        livraison.dateBonCommande = livraison.dateBonCommande != null ? moment(livraison.dateBonCommande) : null;
        livraison.dateBonLivraison = livraison.dateBonLivraison != null ? moment(livraison.dateBonLivraison) : null;
        livraison.dateBonCaisse = livraison.dateBonCaisse != null ? moment(livraison.dateBonCaisse) : null;
      });
    }
    return res;
  }
}
