import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable, of, concat } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap, startWith } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITarifVente, TarifVente } from 'app/shared/model/tarif-vente.model';
import { TarifVenteService } from './tarif-vente.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

@Component({
  selector: 'jhi-tarif-vente-update',
  templateUrl: './tarif-vente-update.component.html'
})
export class TarifVenteUpdateComponent implements OnInit {
  isSaving: boolean;

  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading:Boolean = false;

  produits$: Observable<IProduit[]>;
  produitInput$ = new Subject<string>();
  produitsLoading:Boolean = false;

  editForm = this.fb.group({
    id: [],
    unite: [null, [Validators.required]],
    prix: [null, [Validators.required]],
    client: [],
    produit: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tarifVenteService: TarifVenteService,
    protected clientService: ClientService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarifVente }) => {
      this.updateForm(tarifVente);
    });
    this.loadClients();
    this.loadProduits();
  }

  updateForm(tarifVente: ITarifVente) {
    this.editForm.patchValue({
      id: tarifVente.id,
      unite: tarifVente.unite,
      prix: tarifVente.prix,
      client: tarifVente.client,
      produit: tarifVente.produit
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarifVente = this.createFromForm();
    if (tarifVente.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifVenteService.update(tarifVente));
    } else {
      this.subscribeToSaveResponse(this.tarifVenteService.create(tarifVente));
    }
  }

  private createFromForm(): ITarifVente {
    return {
      ...new TarifVente(),
      id: this.editForm.get(['id']).value,
      unite: this.editForm.get(['unite']).value,
      prix: this.editForm.get(['prix']).value,
      client: this.editForm.get(['client']).value,
      produit: this.editForm.get(['produit']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifVente>>) {
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

  trackClientById(index: number, item: IClient) {
    return item.id;
  }

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }

  private loadClients(){
    this.clients$ = concat(
            of([]), // default items
            this.clientInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.clientsLoading = true)),
                switchMap(nom =>
                    this.clientService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IClient[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.clientsLoading = false))
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
