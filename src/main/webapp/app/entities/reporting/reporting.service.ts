import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILivraison } from 'app/shared/model/livraison.model';
import { IRecapitulatifAchat } from 'app/shared/model/recapitulatif-achat.model';
import { IRecapitulatifVenteClient } from 'app/shared/model/recapitulatif-vente-client.model';
import { IRecapitulatifVenteChauffeur } from 'app/shared/model/recapitulatif-vente-chauffeur.model';
import { IRecapitulatifVenteFacturation } from 'app/shared/model/recapitulatif-vente-facturation.model';
import { IRecapitulatifVenteCaCamion } from 'app/shared/model/recapitulatif-vente-ca-camion.model';
import { IRecapitulatifChargesGasoil } from 'app/shared/model/recapitulatif-gasoil-charges.model';

type IRecapitulatifAchatsResponseType = HttpResponse<IRecapitulatifAchat[]>;
type ILivraisonResponseType = HttpResponse<ILivraison[]>;
type IRecapitulatifVenteClientResponseType = HttpResponse<IRecapitulatifVenteClient[]>;
type IRecapitulatifVenteChauffeurResponseType = HttpResponse<IRecapitulatifVenteChauffeur[]>;
type IRecapitulatifVenteFacturationResponseType = HttpResponse<IRecapitulatifVenteFacturation[]>;
type IRecapitulatifVenteCaCamionResponseType = HttpResponse<IRecapitulatifVenteCaCamion[]>;
type IRecapitulatifGasoilChargesResponseType = HttpResponse<IRecapitulatifChargesGasoil[]>;


@Injectable({ providedIn: 'root' })
export class ReportingService {
  public resourceUrl = SERVER_API_URL + 'api/livraisons';
  public resourceUrlGasoil = SERVER_API_URL + 'api/gasoils';

  constructor(protected http: HttpClient) {}


  getReportingAchat(req?: any): Observable<IRecapitulatifAchatsResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecapitulatifAchat[]>(`${this.resourceUrl}/achat`, { params: options, observe: 'response' })
      .pipe(map((res: IRecapitulatifAchatsResponseType) => this.convertDateArrayFromServer(res)));
  }

  getReportingAchatTrajet(req?: any): Observable<ILivraisonResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraison[]>(`${this.resourceUrl}/achat/trajet`, { params: options, observe: 'response' })
      .pipe(map((res: ILivraisonResponseType) => res));
  }

  getReportingVenteClient(req?: any): Observable<IRecapitulatifVenteClientResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecapitulatifVenteClient[]>(`${this.resourceUrl}/vente/client`, { params: options, observe: 'response' })
      .pipe(map((res: IRecapitulatifVenteClientResponseType) => this.convertRecapClientDateArrayFromServer(res)));
  }

  getReportingVenteChauffeur(req?: any): Observable<IRecapitulatifVenteChauffeurResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecapitulatifVenteChauffeur[]>(`${this.resourceUrl}/vente/chauffeur`, { params: options, observe: 'response' })
      .pipe(map((res: IRecapitulatifVenteChauffeurResponseType) => res));
  }

  exportReporting(req?: any, uri?: string): void {
    const options: HttpParams = createRequestOption(req);
    const url = `${this.resourceUrl}${uri}`;
    this.buildGetRequest(url, options);
  }

  getReportingVenteFacturation(req?: any): Observable<IRecapitulatifVenteFacturationResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecapitulatifVenteFacturation[]>(`${this.resourceUrl}/vente/facturation`, { params: options, observe: 'response' })
      .pipe(map((res: IRecapitulatifVenteFacturationResponseType) => res));
  }

  getReportingVenteCaCamion(req?: any): Observable<IRecapitulatifVenteCaCamionResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecapitulatifVenteCaCamion[]>(`${this.resourceUrl}/vente/ca-camion`, { params: options, observe: 'response' })
      .pipe(map((res: IRecapitulatifVenteCaCamionResponseType) => res));
  }

  getReportingGasoilCharges(req?: any): Observable<IRecapitulatifGasoilChargesResponseType> {
      const options = createRequestOption(req);
      return this.http
        .get<IRecapitulatifChargesGasoil[]>(`${this.resourceUrlGasoil}/charges`, { params: options, observe: 'response' })
        .pipe(map((res: IRecapitulatifGasoilChargesResponseType) => res));
  }

  protected convertDateArrayFromServer(res: IRecapitulatifAchatsResponseType): IRecapitulatifAchatsResponseType {
    if (res.body) {
      res.body.forEach((recapitulatifAchat: IRecapitulatifAchat) => {
        recapitulatifAchat.dateBonCommande = recapitulatifAchat.dateBonCommande != null ? moment(recapitulatifAchat.dateBonCommande) : null;
      });
    }
    return res;
  }

  protected convertRecapClientDateArrayFromServer(res: IRecapitulatifVenteClientResponseType): IRecapitulatifVenteClientResponseType {
    if (res.body) {
      res.body.forEach((recapitulatifVenteClient: IRecapitulatifVenteClient) => {
        recapitulatifVenteClient.dateBonLivraison = recapitulatifVenteClient.dateBonLivraison != null ? moment(recapitulatifVenteClient.dateBonLivraison) : null;
      });
    }
    return res;
  }

  private buildGetRequest(url, options){
    const length = options.keys().length;
        if(length > 0){
          url = url.concat('?');
          let i = 0;
          options.keys().forEach(key => {
             url = url.concat(key).concat('=').concat(options.get(key));
             if(i++ < length-1){
               url = url.concat('&');
             }
          });
        }
        window.open(url , '_blank');
  }
}
