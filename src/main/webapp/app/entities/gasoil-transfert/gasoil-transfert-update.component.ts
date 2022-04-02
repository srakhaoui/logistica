import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IGasoilTransfert, GasoilTransfert } from 'app/shared/model/gasoil-transfert.model';
import { GasoilTransfertService } from './gasoil-transfert.service';
import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from 'app/entities/depot/depot.service';

@Component({
  selector: 'jhi-gasoil-transfert-update',
  templateUrl: './gasoil-transfert-update.component.html',
  styleUrls: ['gasoil-transfert-update.component.scss']
})
export class GasoilTransfertUpdateComponent implements OnInit {
  isSaving: boolean;

  depots: IDepot[];
  transfertDateDp: any;

  selectedSource: IDepot;
  sourceStock: number;
  quantiteSaisie: number;

  editForm = this.fb.group({
    id: [],
    transfertDate: [null],
    quantite: [null, [Validators.required, Validators.min(0)]],
    source: [null, Validators.required],
    destination: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected gasoilTransfertService: GasoilTransfertService,
    protected depotService: DepotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.initForm();
    this.activatedRoute.data.subscribe(({ gasoilTransfert }) => {
      this.updateForm(gasoilTransfert);
    });
    this.depotService
      .query()
      .subscribe((res: HttpResponse<IDepot[]>) => (this.depots = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  private initForm(){
      this.editForm.get('transfertDate').setValue(moment(new Date()));
  }

  updateForm(gasoilTransfert: IGasoilTransfert) {
    this.editForm.patchValue({
      id: gasoilTransfert.id,
      transfertDate: gasoilTransfert.transfertDate,
      quantite: gasoilTransfert.quantite,
      source: gasoilTransfert.source,
      destination: gasoilTransfert.destination
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const gasoilTransfert = this.createFromForm();
    if (gasoilTransfert.id !== undefined) {
      this.subscribeToSaveResponse(this.gasoilTransfertService.update(gasoilTransfert));
    } else {
      this.subscribeToSaveResponse(this.gasoilTransfertService.create(gasoilTransfert));
    }
  }

  private createFromForm(): IGasoilTransfert {
    return {
      ...new GasoilTransfert(),
      id: this.editForm.get(['id']).value,
      transfertDate: moment(new Date()),
      quantite: this.editForm.get(['quantite']).value,
      source: this.editForm.get(['source']).value,
      destination: this.editForm.get(['destination']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGasoilTransfert>>) {
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

  trackDepotById(index: number, item: IDepot) {
    return item.id;
  }

  loadSelectedSourceStock(){
    this.depotService
          .getStock(this.selectedSource.nom)
          .subscribe((res: HttpResponse<number>) => (this.sourceStock = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  onSaisieQuantite(quantite){
    this.quantiteSaisie = quantite;
  }

}
