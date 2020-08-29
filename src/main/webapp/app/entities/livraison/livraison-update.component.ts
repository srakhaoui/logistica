import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormControl, FormGroup, ValidationErrors } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable, of, concat } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap, startWith } from 'rxjs/operators';
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
import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';
import { Unite } from 'app/shared/model/enumerations/unite.model';

@Component({
  selector: 'jhi-livraison-update',
  templateUrl: './livraison-update.component.html'
})
export class LivraisonUpdateComponent implements OnInit {
  isSaving: boolean;

  fournisseurs$: Observable<IFournisseur[]>;
  fournisseurInput$ = new Subject<string>();
  fournisseursLoading:Boolean = false;

  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading:Boolean = false;

  transporteurs$: Observable<ITransporteur[]>;
  transporteurInput$ = new Subject<string>();
  transporteursLoading:Boolean = false;

  trajets$: Observable<ITrajet[]>;
  trajetInput$ = new Subject<string>();
  trajetsLoading:Boolean = false;

  produits$: Observable<IProduit[]>;
  produitInput$ = new Subject<string>();
  produitsLoading:Boolean = false;

  societes: ISociete[];
  dateBonCommandeDp: any;
  dateBonLivraisonDp: any;
  dateBonCaisseDp: any;

  editForm = new FormGroup({
    id: new FormControl(),
    numeroBonCommande: new FormControl(),
    dateBonCommande: new FormControl(),
    bonCommande: new FormControl(),
    numeroBonLivraison: new FormControl(null, Validators.required),
    dateBonLivraison: new FormControl(null, Validators.required),
    bonLivraison: new FormControl(),
    numeroBonFournisseur: new FormControl(),
    bonFournisseur: new FormControl(),
    quantiteVendue: new FormControl(null, Validators.min(0)),
    uniteVente: new FormControl(null, [Validators.required]),
    prixTotalVente: new FormControl(),
    quantiteAchetee: new FormControl(null, Validators.min(0)),
    uniteAchat: new FormControl(),
    prixTotalAchat: new FormControl(),
    quantiteConvertie: new FormControl(),
    type: new FormControl(null, Validators.required),
    facture: new FormControl(),
    dateBonCaisse: new FormControl(null, [Validators.required]),
    reparationDivers: new FormControl(0, Validators.min(0)),
    trax: new FormControl(null, [Validators.min(0)]),
    balance: new FormControl(null, [Validators.min(0)]),
    avance: new FormControl(null, [Validators.min(0)]),
    autoroute: new FormControl(null, [Validators.min(0)]),
    dernierEtat: new FormControl(null, [Validators.min(0)]),
    penaliteEse: new FormControl(null, [Validators.min(0)]),
    penaliteChfrs: new FormControl(null, [Validators.min(0)]),
    fraisEspece: new FormControl(null, [Validators.min(0)]),
    retenu: new FormControl(null, [Validators.min(0)]),
    totalComission: new FormControl(),
    fournisseur: new FormControl(),
    client: new FormControl(null, [Validators.required]),
    transporteur: new FormControl(null, [Validators.required]),
    trajet: new FormControl(),
    produit: new FormControl(null, [Validators.required]),
    societeFacturation: new FormControl(null, [Validators.required]),
    chantier: new FormControl()
  }, [this.validateLivraison]);

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
  ) { }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ livraison }) => {
      this.updateForm(livraison);
    });
    this.loadFournisseurs();
    this.loadClients();
    this.loadTransporteurs();
    this.loadTrajets();
    this.loadProduits();
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    if(this.editForm.get('transporteur').value) {
      const transporteur: ITransporteur = this.editForm.get('transporteur').value;
      transporteur.description = `${transporteur.nom} - ${transporteur.prenom} - ${transporteur.matricule}`;
    }
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
      uniteVente: livraison.id ? livraison.uniteVente : 0,
      prixTotalVente: livraison.prixTotalVente,
      quantiteAchetee: livraison.id ? livraison.quantiteAchetee : 0,
      uniteAchat: livraison.uniteAchat,
      prixTotalAchat: livraison.prixTotalAchat,
      quantiteConvertie: livraison.quantiteConvertie,
      type: livraison.id ? livraison.type : TypeLivraison.Transport,
      facture: livraison.id ? livraison.facture : true,
      dateBonCaisse: livraison.id ? livraison.dateBonCaisse: moment(new Date()),
      reparationDivers: livraison.id ? livraison.reparationDivers : 0,
      trax: livraison.id ?livraison.trax : 0,
      balance: livraison.id ?livraison.balance : 0,
      avance: livraison.id ?livraison.avance : 0,
      autoroute: livraison.id ?livraison.autoroute : 0,
      dernierEtat: livraison.id ?livraison.dernierEtat : 0,
      penaliteEse: livraison.id ?livraison.penaliteEse : 0,
      penaliteChfrs: livraison.id ?livraison.penaliteChfrs : 0,
      fraisEspece: livraison.id ?livraison.fraisEspece : 0,
      retenu: livraison.id ?livraison.retenu : 0,
      totalComission: livraison.totalComission,
      fournisseur: livraison.fournisseur,
      client: livraison.client,
      transporteur: livraison.transporteur,
      trajet: livraison.trajet,
      produit: livraison.produit,
      societeFacturation: livraison.societeFacturation,
      chantier: livraison.chantier
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
      bonCommande: this.editForm.get(['bonCommande']).value,
      numeroBonLivraison: this.editForm.get(['numeroBonLivraison']).value,
      dateBonLivraison: this.editForm.get(['dateBonLivraison']).value,
      bonLivraison: this.editForm.get(['bonLivraison']).value,
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
      societeFacturation: this.editForm.get(['societeFacturation']).value,
      chantier: this.editForm.get(['chantier']).value
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
                        .query({'nom.contains': nom})
                        .pipe(
                          map((resp: HttpResponse<ITransporteur[]>) => resp.body),
                          catchError(() => of([])),
                          map((transporteurs: ITransporteur[]) => {
                            const enriched:ITransporteur[] = [];
                            transporteurs.forEach(transporteur => {
                              transporteur.description = `${transporteur.nom} - ${transporteur.prenom} - ${transporteur.matricule}`
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

  public isMarchandise(): Boolean {
    return this.editForm.get('type').value === TypeLivraison.Marchandise;
  }

  public isTransport(): Boolean {
    return this.editForm.get('type').value === TypeLivraison.Transport;
  }

  validateLivraison(formGroup: FormGroup): ValidationErrors {
      const typeLivraison = formGroup.get('type').value;
      if(typeLivraison === TypeLivraison.Marchandise){
        const otherThanMarchandise: boolean = typeLivraison !== TypeLivraison.Marchandise;
        const isValid = otherThanMarchandise || (
            formGroup.get('fournisseur').value !== undefined &&
            (formGroup.get('uniteAchat').value === Unite.Voyage || (formGroup.get('uniteAchat').value && formGroup.get('uniteAchat').value !== Unite.Voyage && formGroup.get('quantiteAchetee').value !== undefined)) &&
            formGroup.get('dateBonCommande').value !== undefined &&
            formGroup.get('numeroBonCommande').value !== undefined &&
            (formGroup.get('uniteVente').value === Unite.Voyage || (formGroup.get('uniteVente').value && formGroup.get('uniteVente').value !== Unite.Voyage && formGroup.get('quantiteVendue').value !== undefined))
          );
        return  isValid ? null : {invalidMarchandise: true};
      }else if(typeLivraison === TypeLivraison.Transport){
        const otherThanTransport: boolean = typeLivraison !== TypeLivraison.Transport;
        const transportValid = formGroup.get('trajet').value !== undefined &&
        (formGroup.get('uniteVente').value === Unite.Voyage || (formGroup.get('uniteVente').value && formGroup.get('uniteVente').value !== Unite.Voyage && formGroup.get('quantiteVendue').value !== undefined));
        const isValid = otherThanTransport || transportValid;
        return  isValid ? null : {invalidTransport: true};
      }else{
        return null;
      }
  }

  onTransporteurChange(){
    const transporteur: ITransporteur = this.editForm.get(['transporteur']).value;
    if(transporteur){
      this.editForm.get(['societeFacturation']).setValue(transporteur.proprietaire);
    }
  }

  onBonLivraisonSelected(event){
    const bonLivraison = event.target.files[0];
    this.editForm.get(['bonLivraison']).setValue(bonLivraison);
  }

  onBonCommandeSelected(event){
    const bonCommande = event.target.files[0];
    this.editForm.get(['bonCommande']).setValue(bonCommande);
  }
}
