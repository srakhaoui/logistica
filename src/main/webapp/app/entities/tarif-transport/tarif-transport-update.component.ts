import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITarifTransport, TarifTransport } from 'app/shared/model/tarif-transport.model';
import { TarifTransportService } from './tarif-transport.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { ITrajet } from 'app/shared/model/trajet.model';
import { TrajetService } from 'app/entities/trajet/trajet.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

@Component({
  selector: 'jhi-tarif-transport-update',
  templateUrl: './tarif-transport-update.component.html'
})
export class TarifTransportUpdateComponent implements OnInit {
  isSaving: boolean;

  clients: IClient[];

  trajets: ITrajet[];

  produits: IProduit[];

  editForm = this.fb.group({
    id: [],
    unite: [null, [Validators.required]],
    prix: [null, [Validators.required]],
    client: [],
    trajet: [],
    produit: []
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
    this.clientService
      .query()
      .subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.trajetService
      .query()
      .subscribe((res: HttpResponse<ITrajet[]>) => (this.trajets = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produitService
      .query()
      .subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body), (res: HttpErrorResponse) => this.onError(res.message));
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
}
