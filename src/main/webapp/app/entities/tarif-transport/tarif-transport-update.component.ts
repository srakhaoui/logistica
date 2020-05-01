import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable, of, concat } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap, startWith } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITarifTransport, TarifTransport } from 'app/shared/model/tarif-transport.model';
import { TarifTransportService } from './tarif-transport.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { ITrajet } from 'app/shared/model/trajet.model';
import { TrajetService } from 'app/entities/trajet/trajet.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { Unite } from 'app/shared/model/enumerations/unite.model';

@Component({
  selector: 'jhi-tarif-transport-update',
  templateUrl: './tarif-transport-update.component.html'
})
export class TarifTransportUpdateComponent implements OnInit {
  isSaving: boolean;

  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading:Boolean = false;

  trajets$: Observable<ITrajet[]>;
  trajetInput$ = new Subject<string>();
  trajetsLoading:Boolean = false;

  produits$: Observable<IProduit[]>;
  produitInput$ = new Subject<string>();
  produitsLoading:Boolean = false;

  editForm = this.fb.group({
    id: [],
    unite: [null, [Validators.required]],
    prix: [null, [Validators.required]],
    client: [null, [Validators.required]],
    trajet: [null, [Validators.required]],
    produit: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tarifTransportService: TarifTransportService,
    protected clientService: ClientService,
    protected trajetService: TrajetService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarifTransport }) => {
      this.updateForm(tarifTransport);
    });
    this.loadClients();
    this.loadTrajets();
    this.loadProduits();
    this.editForm.get('unite').setValue(Unite.Voyage);
  }

  updateForm(tarifTransport: ITarifTransport) {
    this.editForm.patchValue({
      id: tarifTransport.id,
      unite: tarifTransport.unite,
      prix: tarifTransport.prix,
      client: tarifTransport.client,
      trajet: tarifTransport.trajet,
      produit: tarifTransport.produit
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarifTransport = this.createFromForm();
    if (tarifTransport.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifTransportService.update(tarifTransport));
    } else {
      this.subscribeToSaveResponse(this.tarifTransportService.create(tarifTransport));
    }
  }

  private createFromForm(): ITarifTransport {
    return {
      ...new TarifTransport(),
      id: this.editForm.get(['id']).value,
      unite: this.editForm.get(['unite']).value,
      prix: this.editForm.get(['prix']).value,
      client: this.editForm.get(['client']).value,
      trajet: this.editForm.get(['trajet']).value,
      produit: this.editForm.get(['produit']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifTransport>>) {
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

  trackTrajetById(index: number, item: ITrajet) {
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

  private loadTrajets(){
    this.trajets$ = concat(
            of([]), // default items
            this.trajetInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.trajetsLoading = true)),
                switchMap(nom =>
                    this.trajetService
                        .query({'description.contains': nom})
                        .pipe(map((resp: HttpResponse<ITrajet[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.trajetsLoading = false))
            )
        );
  }
}
