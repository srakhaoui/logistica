import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable, of, concat } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap, startWith } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGasoilVenteGros, GasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';
import { UniteGasoilGros } from 'app/shared/model/enumerations/unite-gasoil-gros.model';
import { GasoilVenteGrosService } from './gasoil-vente-gros.service';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';
import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { ClientGrossisteService } from 'app/entities/client-grossiste/client-grossiste.service';
import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';
import { GasoilAchatGrosService } from 'app/entities/gasoil-achat-gros/gasoil-achat-gros.service';

@Component({
  selector: 'jhi-gasoil-vente-gros-update',
  templateUrl: './gasoil-vente-gros-update.component.html'
})
export class GasoilVenteGrosUpdateComponent implements OnInit {
  isSaving: boolean;

  societes: ISociete[];

  clientsGrossistes$: Observable<IClientGrossiste[]>;
  clientsGrossistesInput$ = new Subject<string>();
  clientsGrossistesLoading:Boolean = false;

  gasoilachatgros$: Observable<IGasoilAchatGros[]>;
  gasoilachatgrosInput$ = new Subject<string>();
  gasoilachatgrosLoading:Boolean = false;


  editForm = this.fb.group({
    id: [],
    prixVenteUnitaire: [null, [Validators.required, Validators.min(0)]],
    quantite: [null, [Validators.required, Validators.min(0)]],
    prixVenteTotal: [null],
    margeGlobale: [null],
    tauxMarge: [null],
    uniteGasoilGros: [null, [Validators.required]],
    transporteur: [null, Validators.required],
    client: [null, Validators.required],
    achatGasoil: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected gasoilVenteGrosService: GasoilVenteGrosService,
    protected societeService: SocieteService,
    protected clientGrossisteService: ClientGrossisteService,
    protected gasoilAchatGrosService: GasoilAchatGrosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ gasoilVenteGros }) => {
      this.updateForm(gasoilVenteGros);
    });
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loadClientsGrossistes();
    this.loadGasoilAchatGros();
  }

  updateForm(gasoilVenteGros: IGasoilVenteGros) {
    this.editForm.patchValue({
      id: gasoilVenteGros.id,
      prixVenteUnitaire: gasoilVenteGros.prixVenteUnitaire,
      quantite: gasoilVenteGros.quantite,
      prixVenteTotal: gasoilVenteGros.prixVenteTotal,
      margeGlobale: gasoilVenteGros.margeGlobale,
      tauxMarge: gasoilVenteGros.tauxMarge,
      uniteGasoilGros: gasoilVenteGros.id ? gasoilVenteGros.uniteGasoilGros : UniteGasoilGros.LITRE,
      transporteur: gasoilVenteGros.transporteur,
      client: gasoilVenteGros.client,
      achatGasoil: gasoilVenteGros.achatGasoil
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const gasoilVenteGros = this.createFromForm();
    if (gasoilVenteGros.id !== undefined) {
      this.subscribeToSaveResponse(this.gasoilVenteGrosService.update(gasoilVenteGros));
    } else {
      this.subscribeToSaveResponse(this.gasoilVenteGrosService.create(gasoilVenteGros));
    }
  }

  private createFromForm(): IGasoilVenteGros {
    return {
      ...new GasoilVenteGros(),
      id: this.editForm.get(['id']).value,
      prixVenteUnitaire: this.editForm.get(['prixVenteUnitaire']).value,
      quantite: this.editForm.get(['quantite']).value,
      prixVenteTotal: this.editForm.get(['prixVenteTotal']).value,
      margeGlobale: this.editForm.get(['margeGlobale']).value,
      tauxMarge: this.editForm.get(['tauxMarge']).value,
      uniteGasoilGros: this.editForm.get(['uniteGasoilGros']).value,
      transporteur: this.editForm.get(['transporteur']).value,
      client: this.editForm.get(['client']).value,
      achatGasoil: this.editForm.get(['achatGasoil']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGasoilVenteGros>>) {
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

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
  }

  trackClientGrossisteById(index: number, item: IClientGrossiste) {
    return item.id;
  }

  trackGasoilAchatGrosById(index: number, item: IGasoilAchatGros) {
    return item.id;
  }

  private loadClientsGrossistes(){
    this.clientsGrossistes$ = concat(
            of([]), // default items
            this.clientsGrossistesInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.clientsGrossistesLoading = true)),
                switchMap(nom =>
                    this.clientGrossisteService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IClientGrossiste[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.clientsGrossistesLoading = false))
            )
        );
  }

  private loadGasoilAchatGros(){
    this.gasoilachatgros$ = concat(
            of([]), // default items
            this.gasoilachatgrosInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.gasoilachatgrosLoading = true)),
                switchMap(nom =>
                    this.gasoilAchatGrosService
                        .query({'description.contains': nom})
                        .pipe(map((resp: HttpResponse<IGasoilAchatGros[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.gasoilachatgrosLoading = false))
            )
        );
  }
}
