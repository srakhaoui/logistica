import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { createRequestOption } from 'app/shared/util/request-util';

import { SERVER_API_URL } from 'app/app.constants';
import { IStatistiquesChiffreAffaire } from 'app/shared/model/statistiques-chiffre-affaire.stats.model';
import { IStatistiquesChiffreAffaireRequest } from 'app/shared/model/statistiques-chiffre-affaire-request.stats.model';

@Injectable({ providedIn: 'root' })
export class StatsService {
  public resourceUrl = SERVER_API_URL + 'api/livraisons/stats';

  constructor(protected http: HttpClient) {}

  getStatistiquesChiffreAffaire(statistiquesChiffreAffaireRequest: IStatistiquesChiffreAffaireRequest): Observable<IStatistiquesChiffreAffaire> {
    return this.http.post<IStatistiquesChiffreAffaire>(`${this.resourceUrl}/chiffre-affaire`, statistiquesChiffreAffaireRequest, { observe: 'body' });
  }

  // /livraisons/stats/repartition-ca/{repartition}/export
  exportStats(repartition: string, req?: any): void {
    const options: HttpParams = createRequestOption(req);
    const url = `${this.resourceUrl}/repartition-ca/${repartition}/export`;
    this.buildGetRequest(url, options);
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
