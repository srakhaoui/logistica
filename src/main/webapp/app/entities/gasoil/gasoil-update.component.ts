import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable, of, concat } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap, startWith } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGasoil, Gasoil } from 'app/shared/model/gasoil.model';
import { GasoilService } from './gasoil.service';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';


@Component({
  selector: 'jhi-gasoil-update',
  templateUrl: './gasoil-update.component.html'
})
export class GasoilUpdateComponent implements OnInit {
  isSaving: boolean;

  transporteurs$: Observable<ITransporteur[]>;
  transporteurInput$ = new Subject<string>();
  transporteursLoading:Boolean = false;

  societes: ISociete[];

  kilometrageInitialReadOnly;

  editForm = this.fb.group({
    id: [],
    numeroBonGasoil: [null, [Validators.required]],
    quantiteEnLitre: [null, [Validators.required]],
    prixDuLitre: [null, [Validators.required]],
    prixTotalGasoil: [],
    kilometrageInitial: [],
    kilometrageFinal: [],
    kilometrageParcouru: [],
    transporteur: [null, [Validators.required]],
    societeFacturation: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected gasoilService: GasoilService,
    protected transporteurService: TransporteurService,
    protected societeService: SocieteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.kilometrageInitialReadOnly = false;
    this.activatedRoute.data.subscribe(({ gasoil }) => {
      this.updateForm(gasoil);
    });
    this.loadTransporteurs();
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    if(this.editForm.get('transporteur').value) {
      const transporteur: ITransporteur = this.editForm.get('transporteur').value;
      transporteur.description = `${transporteur.nom} - ${transporteur.prenom} - ${transporteur.matricule}`;
    }
  }

  updateForm(gasoil: IGasoil) {
    this.editForm.patchValue({
      id: gasoil.id,
      numeroBonGasoil: gasoil.numeroBonGasoil,
      quantiteEnLitre: gasoil.quantiteEnLitre,
      prixDuLitre: gasoil.prixDuLitre,
      prixTotalGasoil: gasoil.prixTotalGasoil,
      kilometrageInitial: gasoil.kilometrageInitial,
      kilometrageFinal: gasoil.kilometrageFinal,
      kilometrageParcouru: gasoil.kilometrageParcouru,
      transporteur: gasoil.transporteur,
      societeFacturation: gasoil.societeFacturation
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const gasoil = this.createFromForm();
    if (gasoil.id !== undefined) {
      this.subscribeToSaveResponse(this.gasoilService.update(gasoil));
    } else {
      this.subscribeToSaveResponse(this.gasoilService.create(gasoil));
    }
  }

  private createFromForm(): IGasoil {
    return {
      ...new Gasoil(),
      id: this.editForm.get(['id']).value,
      numeroBonGasoil: this.editForm.get(['numeroBonGasoil']).value,
      quantiteEnLitre: this.editForm.get(['quantiteEnLitre']).value,
      prixDuLitre: this.editForm.get(['prixDuLitre']).value,
      prixTotalGasoil: this.editForm.get(['prixTotalGasoil']).value,
      kilometrageInitial: this.editForm.get(['kilometrageInitial']).value,
      kilometrageFinal: this.editForm.get(['kilometrageFinal']).value,
      kilometrageParcouru: this.editForm.get(['kilometrageParcouru']).value,
      transporteur: this.editForm.get(['transporteur']).value,
      societeFacturation: this.editForm.get(['societeFacturation']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGasoil>>) {
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

  trackTransporteurById(index: number, item: ITransporteur) {
    return item.id;
  }

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
  }

  private loadTransporteurs(){
    this.transporteurs$ = concat(
            of([]), // default items
            this.transporteurInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.transporteursLoading = true)),
                switchMap(nom =>
                    this.transporteurService
                        .query({'matricule.contains': nom})
                        .pipe(
                          map((resp: HttpResponse<ITransporteur[]>) => resp.body),
                          catchError(() => of([])),
                          map((transporteurs: ITransporteur[]) => {
                            const enriched:ITransporteur[] = [];
                            transporteurs.forEach(transporteur => {
                              transporteur.description = `${transporteur.matricule} - ${transporteur.nom} - ${transporteur.prenom}`
                              enriched.push(transporteur);
                            });
                            return enriched;
                          })
                         )
                ),
                tap(() => (this.transporteursLoading = false))
            )
    );
  }


  onTransporteurChange(){
    const transporteur: ITransporteur = this.editForm.get(['transporteur']).value;
    if(transporteur){
      this.editForm.get(['societeFacturation']).setValue(transporteur.proprietaire);
      const req = {matricule: transporteur.matricule}
      this.gasoilService.getKilometrageFinal(req).subscribe(kilometrageFinal => {
        this.kilometrageInitialReadOnly = true;
        this.editForm.get(['kilometrageInitial']).setValue(kilometrageFinal.body);
      },
       () => this.onSaveError());
    }
  }
}
