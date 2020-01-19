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
import { IRecapitulatifAchat } from 'app/shared/model/recapitulatif-achat.model';

type IRecapitulatifAchatsResponseType = HttpResponse<IRecapitulatifAchat[]>;

@Injectable({ providedIn: 'root' })
export class ReportingService {
  public resourceUrl = SERVER_API_URL + 'api/livraisons';

  constructor(protected http: HttpClient) {}


  getReportingAchat(req?: any): Observable<IRecapitulatifAchatsResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecapitulatifAchat[]>(`${this.resourceUrl}/achat`, { params: options, observe: 'response' })
      .pipe(map((res: IRecapitulatifAchatsResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateArrayFromServer(res: IRecapitulatifAchatsResponseType): IRecapitulatifAchatsResponseType {
    if (res.body) {
      res.body.forEach((recapitulatifAchat: IRecapitulatifAchat) => {
        recapitulatifAchat.dateBonCommande = recapitulatifAchat.dateBonCommande != null ? moment(recapitulatifAchat.dateBonCommande) : null;
      });
    }
    return res;
  }
}
