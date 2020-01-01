import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITarifAchat, TarifAchat } from 'app/shared/model/tarif-achat.model';
import { TarifAchatService } from './tarif-achat.service';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/fournisseur.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

@Component({
  selector: 'jhi-tarif-achat-update',
  templateUrl: './tarif-achat-update.component.html'
})
export class TarifAchatUpdateComponent implements OnInit {
  isSaving: boolean;

  fournisseurs: IFournisseur[];

  produits: IProduit[];

  editForm = this.fb.group({
    id: [],
    unite: [null, [Validators.required]],
    prix: [null, [Validators.required]],
    fournisseur: [],
    produit: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tarifAchatService: TarifAchatService,
    protected fournisseurService: FournisseurService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarifAchat }) => {
      this.updateForm(tarifAchat);
    });
    this.fournisseurService
      .query()
      .subscribe(
        (res: HttpResponse<IFournisseur[]>) => (this.fournisseurs = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.produitService
      .query()
      .subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tarifAchat: ITarifAchat) {
    this.editForm.patchValue({
      id: tarifAchat.id,
      unite: tarifAchat.unite,
      prix: tarifAchat.prix,
      fournisseur: tarifAchat.fournisseur,
      produit: tarifAchat.produit
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarifAchat = this.createFromForm();
    if (tarifAchat.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifAchatService.update(tarifAchat));
    } else {
      this.subscribeToSaveResponse(this.tarifAchatService.create(tarifAchat));
    }
  }

  private createFromForm(): ITarifAchat {
    return {
      ...new TarifAchat(),
      id: this.editForm.get(['id']).value,
      unite: this.editForm.get(['unite']).value,
      prix: this.editForm.get(['prix']).value,
      fournisseur: this.editForm.get(['fournisseur']).value,
      produit: this.editForm.get(['produit']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifAchat>>) {
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

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }
}
