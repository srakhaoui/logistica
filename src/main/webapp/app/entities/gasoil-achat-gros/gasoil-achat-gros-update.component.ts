import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable, of, concat } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap, startWith } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IGasoilAchatGros, GasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';
import { UniteGasoilGros } from 'app/shared/model/enumerations/unite-gasoil-gros.model';
import { GasoilAchatGrosService } from './gasoil-achat-gros.service';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/fournisseur.service';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

@Component({
  selector: 'jhi-gasoil-achat-gros-update',
  templateUrl: './gasoil-achat-gros-update.component.html'
})
export class GasoilAchatGrosUpdateComponent implements OnInit {
  isSaving: boolean;

  fournisseurs$: Observable<IFournisseur[]>;
  fournisseurInput$ = new Subject<string>();
  fournisseursLoading:Boolean = false;

  societes: ISociete[];

  produits$: Observable<IProduit[]>;
  produitInput$ = new Subject<string>();
  produitsLoading:Boolean = false;

  dateReceptionDp: any;

  editForm = this.fb.group({
    id: [],
    dateReception: [null, [Validators.required]],
    numeroBonReception: [null, [Validators.required, Validators.minLength(1)]],
    description: [],
    quantity: [null, [Validators.required, Validators.min(0)]],
    prixUnitaire: [null, [Validators.required, Validators.min(0)]],
    uniteGasoilGros: [null, [Validators.required]],
    fournisseur: [null, Validators.required],
    transporteur: [null, Validators.required],
    produit: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected gasoilAchatGrosService: GasoilAchatGrosService,
    protected fournisseurService: FournisseurService,
    protected societeService: SocieteService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ gasoilAchatGros }) => {
      this.updateForm(gasoilAchatGros);
    });
    this.loadFournisseurs();
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loadProduits();
  }

  updateForm(gasoilAchatGros: IGasoilAchatGros) {
    this.editForm.patchValue({
      id: gasoilAchatGros.id,
      dateReception: gasoilAchatGros.dateReception,
      numeroBonReception: gasoilAchatGros.numeroBonReception,
      description: gasoilAchatGros.description,
      quantity: gasoilAchatGros.quantity,
      prixUnitaire: gasoilAchatGros.prixUnitaire,
      uniteGasoilGros: gasoilAchatGros.id ? gasoilAchatGros.uniteGasoilGros : UniteGasoilGros.TONNE,
      fournisseur: gasoilAchatGros.fournisseur,
      transporteur: gasoilAchatGros.transporteur,
      produit: gasoilAchatGros.produit
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const gasoilAchatGros = this.createFromForm();
    if (gasoilAchatGros.id !== undefined) {
      this.subscribeToSaveResponse(this.gasoilAchatGrosService.update(gasoilAchatGros));
    } else {
      this.subscribeToSaveResponse(this.gasoilAchatGrosService.create(gasoilAchatGros));
    }
  }

  private createFromForm(): IGasoilAchatGros {
    return {
      ...new GasoilAchatGros(),
      id: this.editForm.get(['id']).value,
      dateReception: this.editForm.get(['dateReception']).value,
      numeroBonReception: this.editForm.get(['numeroBonReception']).value,
      description: this.editForm.get(['description']).value,
      quantity: this.editForm.get(['quantity']).value,
      prixUnitaire: this.editForm.get(['prixUnitaire']).value,
      uniteGasoilGros: this.editForm.get(['uniteGasoilGros']).value,
      fournisseur: this.editForm.get(['fournisseur']).value,
      transporteur: this.editForm.get(['transporteur']).value,
      produit: this.editForm.get(['produit']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGasoilAchatGros>>) {
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

  trackFournisseurById(index: number, item: IFournisseur) {
    return item.id;
  }

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
  }

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }

  private loadFournisseurs(){
    this.fournisseurs$ = concat(
            of([]), // default items
            this.fournisseurInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.fournisseursLoading = true)),
                switchMap(nom =>
                    this.fournisseurService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IFournisseur[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.fournisseursLoading = false))
            )
        );
  }

  private loadProduits(){
    this.produits$ = concat(
            of([]), // default items
            this.produitInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.produitsLoading = true)),
                switchMap(nom =>
                    this.produitService
                        .query({'code.contains': nom})
                        .pipe(map((resp: HttpResponse<IProduit[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.produitsLoading = false))
            )
        );
  }
}
