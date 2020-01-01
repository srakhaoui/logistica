import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILivraison, Livraison } from 'app/shared/model/livraison.model';
import { LivraisonService } from './livraison.service';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/fournisseur.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { ITrajet } from 'app/shared/model/trajet.model';
import { TrajetService } from 'app/entities/trajet/trajet.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';

@Component({
  selector: 'jhi-livraison-update',
  templateUrl: './livraison-update.component.html'
})
export class LivraisonUpdateComponent implements OnInit {
  isSaving: boolean;

  fournisseurs: IFournisseur[];

  clients: IClient[];

  transporteurs: ITransporteur[];

  trajets: ITrajet[];

  produits: IProduit[];

  societes: ISociete[];
  dateBonCommandeDp: any;
  dateBonLivraisonDp: any;
  dateBonCaisseDp: any;

  editForm = this.fb.group({
    id: [],
    dateBonCommande: [],
    numeroBonCommande: [],
    numeroBonLivraison: [null, [Validators.required]],
    dateBonLivraison: [null, [Validators.required]],
    numeroBonFournisseur: [],
    quantiteVendue: [],
    uniteVente: [],
    prixTotalVente: [],
    quantiteAchetee: [],
    uniteAchat: [],
    prixTotalAchat: [],
    quantiteConvertie: [],
    type: [null, [Validators.required]],
    facture: [],
    dateBonCaisse: [null, [Validators.required]],
    reparationDivers: [],
    trax: [],
    balance: [],
    avance: [],
    autoroute: [],
    dernierEtat: [],
    penaliteEse: [],
    penaliteChfrs: [],
    fraisEspece: [],
    retenu: [],
    totalComission: [],
    fournisseur: [],
    client: [],
    transporteur: [],
    trajet: [],
    produit: [],
    societeFacturation: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected livraisonService: LivraisonService,
    protected fournisseurService: FournisseurService,
    protected clientService: ClientService,
    protected transporteurService: TransporteurService,
    protected trajetService: TrajetService,
    protected produitService: ProduitService,
    protected societeService: SocieteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ livraison }) => {
      this.updateForm(livraison);
    });
    this.fournisseurService
      .query()
      .subscribe(
        (res: HttpResponse<IFournisseur[]>) => (this.fournisseurs = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.clientService
      .query()
      .subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.transporteurService
      .query()
      .subscribe(
        (res: HttpResponse<ITransporteur[]>) => (this.transporteurs = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.trajetService
      .query()
      .subscribe((res: HttpResponse<ITrajet[]>) => (this.trajets = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produitService
      .query()
      .subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(livraison: ILivraison) {
    this.editForm.patchValue({
      id: livraison.id,
      dateBonCommande: livraison.dateBonCommande,
      numeroBonCommande: livraison.numeroBonCommande,
      numeroBonLivraison: livraison.numeroBonLivraison,
      dateBonLivraison: livraison.dateBonLivraison,
      numeroBonFournisseur: livraison.numeroBonFournisseur,
      quantiteVendue: livraison.quantiteVendue,
      uniteVente: livraison.uniteVente,
      prixTotalVente: livraison.prixTotalVente,
      quantiteAchetee: livraison.quantiteAchetee,
      uniteAchat: livraison.uniteAchat,
      prixTotalAchat: livraison.prixTotalAchat,
      quantiteConvertie: livraison.quantiteConvertie,
      type: livraison.type,
      facture: livraison.facture,
      dateBonCaisse: livraison.dateBonCaisse,
      reparationDivers: livraison.reparationDivers,
      trax: livraison.trax,
      balance: livraison.balance,
      avance: livraison.avance,
      autoroute: livraison.autoroute,
      dernierEtat: livraison.dernierEtat,
      penaliteEse: livraison.penaliteEse,
      penaliteChfrs: livraison.penaliteChfrs,
      fraisEspece: livraison.fraisEspece,
      retenu: livraison.retenu,
      totalComission: livraison.totalComission,
      fournisseur: livraison.fournisseur,
      client: livraison.client,
      transporteur: livraison.transporteur,
      trajet: livraison.trajet,
      produit: livraison.produit,
      societeFacturation: livraison.societeFacturation
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const livraison = this.createFromForm();
    if (livraison.id !== undefined) {
      this.subscribeToSaveResponse(this.livraisonService.update(livraison));
    } else {
      this.subscribeToSaveResponse(this.livraisonService.create(livraison));
    }
  }

  private createFromForm(): ILivraison {
    return {
      ...new Livraison(),
      id: this.editForm.get(['id']).value,
      dateBonCommande: this.editForm.get(['dateBonCommande']).value,
      numeroBonCommande: this.editForm.get(['numeroBonCommande']).value,
      numeroBonLivraison: this.editForm.get(['numeroBonLivraison']).value,
      dateBonLivraison: this.editForm.get(['dateBonLivraison']).value,
      numeroBonFournisseur: this.editForm.get(['numeroBonFournisseur']).value,
      quantiteVendue: this.editForm.get(['quantiteVendue']).value,
      uniteVente: this.editForm.get(['uniteVente']).value,
      prixTotalVente: this.editForm.get(['prixTotalVente']).value,
      quantiteAchetee: this.editForm.get(['quantiteAchetee']).value,
      uniteAchat: this.editForm.get(['uniteAchat']).value,
      prixTotalAchat: this.editForm.get(['prixTotalAchat']).value,
      quantiteConvertie: this.editForm.get(['quantiteConvertie']).value,
      type: this.editForm.get(['type']).value,
      facture: this.editForm.get(['facture']).value,
      dateBonCaisse: this.editForm.get(['dateBonCaisse']).value,
      reparationDivers: this.editForm.get(['reparationDivers']).value,
      trax: this.editForm.get(['trax']).value,
      balance: this.editForm.get(['balance']).value,
      avance: this.editForm.get(['avance']).value,
      autoroute: this.editForm.get(['autoroute']).value,
      dernierEtat: this.editForm.get(['dernierEtat']).value,
      penaliteEse: this.editForm.get(['penaliteEse']).value,
      penaliteChfrs: this.editForm.get(['penaliteChfrs']).value,
      fraisEspece: this.editForm.get(['fraisEspece']).value,
      retenu: this.editForm.get(['retenu']).value,
      totalComission: this.editForm.get(['totalComission']).value,
      fournisseur: this.editForm.get(['fournisseur']).value,
      client: this.editForm.get(['client']).value,
      transporteur: this.editForm.get(['transporteur']).value,
      trajet: this.editForm.get(['trajet']).value,
      produit: this.editForm.get(['produit']).value,
      societeFacturation: this.editForm.get(['societeFacturation']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivraison>>) {
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

  trackClientById(index: number, item: IClient) {
    return item.id;
  }

  trackTransporteurById(index: number, item: ITransporteur) {
    return item.id;
  }

  trackTrajetById(index: number, item: ITrajet) {
    return item.id;
  }

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
  }
}
