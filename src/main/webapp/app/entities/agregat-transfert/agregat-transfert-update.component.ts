import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAgregatTransfert, AgregatTransfert } from 'app/shared/model/agregat-transfert.model';
import { AgregatTransfertService } from './agregat-transfert.service';
import { IDepotAggregat } from 'app/shared/model/depot-aggregat.model';
import { DepotAggregatService } from 'app/entities/depot-aggregat/depot-aggregat.service';
import { ReportingService } from 'app/entities/reporting/reporting.service';
import { IRecapitulatifDepotAgregatStock } from 'app/shared/model/recapitulatif-depot-agregat-stock.model';

@Component({
  selector: 'jhi-agregat-transfert-update',
  templateUrl: './agregat-transfert-update.component.html',
  styleUrls: ['./agregat-transfert-update.component.scss']
})
export class AgregatTransfertUpdateComponent implements OnInit {
  isSaving: boolean;

  depotaggregats: IDepotAggregat[];

  stockDepotSource: number;

  editForm = this.fb.group({
    id: [],
    transfertDate: [],
    quantite: [null, [Validators.required, Validators.min(0)]],
    unite: [null, [Validators.required]],
    source: [null, Validators.required],
    destination: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agregatTransfertService: AgregatTransfertService,
    protected depotAggregatService: DepotAggregatService,
    protected reportingService: ReportingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agregatTransfert }) => {
      this.updateForm(agregatTransfert);
    });
    this.depotAggregatService
      .query()
      .subscribe(
        (res: HttpResponse<IDepotAggregat[]>) => (this.depotaggregats = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(agregatTransfert: IAgregatTransfert) {
    this.editForm.patchValue({
      id: agregatTransfert.id,
      transfertDate: agregatTransfert.transfertDate,
      quantite: agregatTransfert.quantite,
      unite: agregatTransfert.unite,
      source: agregatTransfert.source,
      destination: agregatTransfert.destination
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agregatTransfert = this.createFromForm();
    if (agregatTransfert.id !== undefined) {
      this.subscribeToSaveResponse(this.agregatTransfertService.update(agregatTransfert));
    } else {
      this.subscribeToSaveResponse(this.agregatTransfertService.create(agregatTransfert));
    }
  }

  private createFromForm(): IAgregatTransfert {
    return {
      ...new AgregatTransfert(),
      id: this.editForm.get(['id']).value,
      transfertDate: this.editForm.get(['transfertDate']).value ? this.editForm.get(['transfertDate']).value : moment(new Date()),
      quantite: this.editForm.get(['quantite']).value,
      unite: this.editForm.get(['unite']).value,
      source: this.editForm.get(['source']).value,
      destination: this.editForm.get(['destination']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgregatTransfert>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDepotAggregatById(index: number, item: IDepotAggregat) {
    return item.id;
  }

  onDepotSourceChange(event) {
    const transferUniteDefined = this.editForm.get('unite') !== undefined;
    const sourceDepotNameDefined = event.nom !== undefined;
    if (sourceDepotNameDefined && transferUniteDefined) {
      const sourceDepotName = event.nom;
      const transferUnite = this.editForm.get('unite');
      this.reportingService.getReportingDepotAgregatStock({}).subscribe((res: HttpResponse<IRecapitulatifDepotAgregatStock[]>) => {
        this.stockDepotSource = res.body.find(recapStock => recapStock.nom === sourceDepotName).stockByUnite[transferUnite.value];
      });
    }
  }

  onUniteChange(event) {
    const transferUniteDefined = event !== undefined;
    const sourceDepotDefined = this.editForm.get('source').value !== undefined;
    if (transferUniteDefined && sourceDepotDefined) {
      const transferUnite = event;
      const sourceDepotName = this.editForm.get('source').value.nom;
      this.reportingService.getReportingDepotAgregatStock({}).subscribe((res: HttpResponse<IRecapitulatifDepotAgregatStock[]>) => {
        this.stockDepotSource = res.body.find(recapStock => recapStock.nom === sourceDepotName).stockByUnite[transferUnite];
      });
    }
  }
}
