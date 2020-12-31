import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { createRequestOption } from 'app/shared/util/request-util';

import { SERVER_API_URL } from 'app/app.constants';
import { IStatistiquesChiffreAffaire } from 'app/shared/model/statistiques-chiffre-affaire.stats.model';
import { IStatistiquesChiffreAffaireRequest } from 'app/shared/model/statistiques-chiffre-affaire-request.stats.model';
import { IStatistiquesTauxRentabilite } from 'app/shared/model/statistiques-taux-rentabilite.stats.model';
import { IStatistiquesTauxRentabiliteRequest } from 'app/shared/model/statistiques-taux-rentabilite-request.stats.model';
import { IStatistiquesTauxConsommation } from 'app/shared/model/statistiques-taux-consommation.stats.model';
import { IStatistiquesTauxConsommationRequest } from 'app/shared/model/statistiques-taux-consommation-request.stats.model';

@Injectable({ providedIn: 'root' })
export class StatsService {
  public resourceUrl = SERVER_API_URL + 'api/livraisons/stats';
  public resourceUrlGasoil = SERVER_API_URL + 'api/gasoils/stats';

  constructor(protected http: HttpClient) {}

  getStatistiquesChiffreAffaire(statistiquesChiffreAffaireRequest: IStatistiquesChiffreAffaireRequest): Observable<IStatistiquesChiffreAffaire> {
    return this.http.post<IStatistiquesChiffreAffaire>(`${this.resourceUrl}/chiffre-affaire`, statistiquesChiffreAffaireRequest, { observe: 'body' });
  }

  exportStats(repartition: string, req?: any): void {
    const options: HttpParams = createRequestOption(req);
    const url = `${this.resourceUrl}/repartition-ca/${repartition}/export`;
    this.buildGetRequest(url, options);
  }

  exportStatsRentabilite(req?: any): void {
    const options: HttpParams = createRequestOption(req);
    const url = `${this.resourceUrl}/taux-rentabilite/export`;
    this.buildGetRequest(url, options);
  }

  getStatistiquesTauxRentabilite(statistiquesTauxRentabiliteRequest: IStatistiquesTauxRentabiliteRequest): Observable<IStatistiquesTauxRentabilite> {
      return this.http.post<IStatistiquesTauxRentabilite>(`${this.resourceUrl}/taux-rentabilite`, statistiquesTauxRentabiliteRequest, { observe: 'body' });
  }

  getStatistiquesTauxConsommation(statistiquesTauxConsommationRequest: IStatistiquesTauxConsommationRequest): Observable<IStatistiquesTauxConsommation> {
      return this.http.post<IStatistiquesTauxConsommation>(`${this.resourceUrlGasoil}/taux-consommation`, statistiquesTauxConsommationRequest, { observe: 'body' });
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
