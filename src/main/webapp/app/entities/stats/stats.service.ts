import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
}
