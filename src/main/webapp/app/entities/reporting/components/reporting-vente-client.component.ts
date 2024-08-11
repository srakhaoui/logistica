import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { concat, Observable, of, Subject } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReportingService } from '../reporting.service';
import { FormControl, FormGroup } from '@angular/forms';
import { ISociete } from 'app/shared/model/societe.model';
import { IClient } from 'app/shared/model/client.model';
import { IProduit } from 'app/shared/model/produit.model';
import { SocieteService } from 'app/entities/societe/societe.service';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ClientService } from 'app/entities/client/client.service';
import { catchError, debounceTime, distinctUntilChanged, finalize, map, startWith, switchMap, tap } from 'rxjs/operators';
import { IRecapitulatifVenteClient } from 'app/shared/model/recapitulatif-vente-client.model';
import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';
import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';
import { ReportingBonComponent } from 'app/entities/reporting/components/reporting-bon.component';
import { FactureService } from 'app/entities/facture/facture.service';
import { IFacturationRequest } from 'app/shared/model/facturation-request.facture.model';
import { FacturationFactureComponent } from 'app/entities/reporting/components/facturation-facture.component';
import { IRecapitulatifVentesClient } from 'app/shared/model/recapitulatif-ventes-client.model';
import { IFacture } from 'app/shared/model/facture.model';
import { IReglementEspeceRequest } from 'app/shared/model/paiement-espece-request.reglement.model';

@Component({
  selector: 'jhi-reporting-vente-client',
  templateUrl: './reporting-vente-client.component.html'
})
export class ReportingVenteClientComponent implements OnInit, OnDestroy {
  societes: ISociete[];

  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading: Boolean = false;

  chantiers: string[] = [];
  chantiersLoading: Boolean = false;

  produits$: Observable<IProduit[]>;
  produitInput$ = new Subject<string>();
  produitsLoading: Boolean = false;

  activatedTab = 'livraisons';

  reportingForm = new FormGroup({
    societe: new FormControl(),
    client: new FormControl(),
    produit: new FormControl(),
    facture: new FormControl(),
    typeLivraison: new FormControl(),
    chantier: new FormControl(),
    dateDebut: new FormControl(),
    dateFin: new FormControl(),
    regleEnEspece: new FormControl()
  });

  recapitulatifs: IRecapitulatifVenteClient[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  factures: IFacture[];
  linksFactures: any;
  pageFactures: any;
  totalItemsFactures: number;

  isSearching: Boolean = false;
  isBilling: Boolean = false;
  isPaying: Boolean = false;

  billing = { montant: 0.0, remise: 0 };

  constructor(
    protected reportingService: ReportingService,
    protected societeService: SocieteService,
    protected clientService: ClientService,
    protected factureService: FactureService,
    protected produitService: ProduitService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.recapitulatifs = [];
    this.initForm();
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.pageFactures = 0;
    this.linksFactures = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  private initForm() {
    const defaultDateDebut = moment(new Date()).startOf('month');
    this.reportingForm.get('dateDebut').setValue(defaultDateDebut);
    const defaultDateFin = moment(new Date()).endOf('month');
    this.reportingForm.get('dateFin').setValue(defaultDateFin);
    this.reportingForm.get('typeLivraison').setValue(TypeLivraison.Transport);
  }

  loadAll() {
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loadClients();
    this.loadProduits();
    this.search();
  }

  search() {
    this.isSearching = true;
    this.reportingService
      .getReportingVenteClient(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifVentesClient>) => {
        this.isSearching = false;
        this.paginateRecapitulatifs(res.body, res.headers);
      });
  }

  private loadClients() {
    this.clients$ = concat(
      of([]), // default items
      this.clientInput$.pipe(
        startWith(''),
        debounceTime(500),
        distinctUntilChanged(),
        tap(() => (this.clientsLoading = true)),
        switchMap(nom =>
          this.clientService.query({ 'nom.contains': nom }).pipe(
            map((resp: HttpResponse<IClient[]>) => resp.body),
            catchError(() => of([]))
          )
        ),
        tap(() => (this.clientsLoading = false))
      )
    );
  }

  private loadProduits() {
    this.produits$ = concat(
      of([]), // default items
      this.produitInput$.pipe(
        startWith(''),
        debounceTime(500),
        distinctUntilChanged(),
        tap(() => (this.produitsLoading = true)),
        switchMap(nom =>
          this.produitService.query({ 'code.contains': nom }).pipe(
            map((resp: HttpResponse<IProduit[]>) => resp.body),
            catchError(() => of([]))
          )
        ),
        tap(() => (this.produitsLoading = false))
      )
    );
  }

  export() {
    this.reportingService.exportReporting(this.buildReportingRequest(), '/vente/client/export');
  }

  private buildReportingRequest(): any {
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.reportingForm.get('societe').value) {
      reportingRequest['societeId'] = this.reportingForm.get('societe').value.id;
    }
    if (this.reportingForm.get('client').value) {
      reportingRequest['clientId'] = this.reportingForm.get('client').value.id;
    }
    if (this.reportingForm.get('produit').value) {
      reportingRequest['produitId'] = this.reportingForm.get('produit').value.id;
    }
    if (this.reportingForm.get('typeLivraison').value) {
      reportingRequest['typeLivraison'] = this.reportingForm.get('typeLivraison').value;
    }
    if (this.reportingForm.get('facture').value !== null) {
      reportingRequest['facture'] = this.reportingForm.get('facture').value;
    }
    if (this.reportingForm.get('chantier').value) {
      reportingRequest['chantier'] = this.reportingForm.get('chantier').value;
    }
    if (this.reportingForm.get('dateDebut').value) {
      reportingRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    if (this.reportingForm.get('dateFin').value) {
      reportingRequest['dateFin'] = format(this.reportingForm.get('dateFin').value);
    }
    if (this.reportingForm.get('regleEnEspece').value !== null) {
      reportingRequest['regleEnEspece'] = this.reportingForm.get('regleEnEspece').value;
    }
    return reportingRequest;
  }

  reset() {
    switch (this.activatedTab) {
      case 'factures': {
        this.pageFactures = 0;
        this.factures = [];
        this.searchFactures();
        break;
      }
      case 'reglements': {
        this.searchReglements();
        break;
      }
      case 'livraisons': {
        this.page = 0;
        this.recapitulatifs = [];
        this.search();
        break;
      }
    }
  }

  loadPage(page) {
    switch (this.activatedTab) {
      case 'factures': {
        this.pageFactures = page;
        this.searchReglements();
        break;
      }
      case 'reglements': {
        break;
      }
      case 'livraison': {
        this.page = page;
        this.search();
        break;
      }
    }
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {}

  trackId(index: number, item: IRecapitulatifVenteClient) {
    return item.client;
  }

  trackFactures(index: number, item: IFacture) {
    return item.client;
  }

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginateRecapitulatifs(data: IRecapitulatifVentesClient, headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.billing['montantFacturationMax'] = data.montantFacturationMax;
    this.billing['montantFacturationMin'] = data.montantFacturationMin;
    for (let i = 0; i < data.recapitulatifClients.length; i++) {
      this.recapitulatifs.push(data.recapitulatifClients[i]);
    }
  }

  showBonLivraison(livraisonId: number) {
    const modalBonRef = this.modalService.open(ReportingBonComponent);
    modalBonRef.componentInstance.livraisonId = livraisonId;
    modalBonRef.componentInstance.bonType = 'Livraison';
  }

  onClientChange() {
    const client: IClient = this.reportingForm.get(['client']).value;
    if (client) {
      this.chantiers = [];
      this.chantiersLoading = true;
      this.reportingService.getChantiersByClient(this.buildChantiersRequest(client.id)).subscribe((res: HttpResponse<string[]>) => {
        this.chantiersLoading = false;
        this.chantiers = Array.from(res.body);
      });
    }
  }

  private buildChantiersRequest(clientIdParam: number): any {
    const chantiersRequest = {
      clientId: clientIdParam
    };
    if (this.reportingForm.get('dateDebut').value) {
      chantiersRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    if (this.reportingForm.get('dateFin').value) {
      chantiersRequest['dateFin'] = format(this.reportingForm.get('dateFin').value);
    }
    return chantiersRequest;
  }

  public isMarchandise(): Boolean {
    return this.reportingForm.get('typeLivraison').value === TypeLivraison.Marchandise;
  }

  activateTab(tabId: string) {
    this.activatedTab = tabId;
  }

  isActivated(tabId: string) {
    return this.activatedTab === tabId;
  }

  private buildBillingRequest(): IFacturationRequest {
    const facturationRequest = {};
    if (this.reportingForm.get('societe').value) {
      facturationRequest['societeId'] = this.reportingForm.get('societe').value.id;
    }
    if (this.reportingForm.get('client').value) {
      facturationRequest['clientId'] = this.reportingForm.get('client').value.id;
    }
    if (this.reportingForm.get('typeLivraison').value) {
      facturationRequest['typeLivraison'] = this.reportingForm.get('typeLivraison').value;
    }
    if (this.reportingForm.get('chantier').value) {
      facturationRequest['chantier'] = this.reportingForm.get('chantier').value;
    }
    if (this.reportingForm.get('dateDebut').value) {
      facturationRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    if (this.reportingForm.get('dateFin').value) {
      facturationRequest['dateFin'] = format(this.reportingForm.get('dateFin').value);
    }
    facturationRequest['remise'] = this.billing.remise / 100;
    facturationRequest['montant'] = this.billing.montant;

    return facturationRequest;
  }

  billEntirely() {
    const modalBonRef = this.modalService.open(FacturationFactureComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalBonRef.componentInstance.billing = this.billing;
    modalBonRef.componentInstance.billingEventEmitter.subscribe(billing => {
      this.billing = billing;
      this.isBilling = true;
      this.factureService
        .facturer(this.buildBillingRequest())
        .pipe(finalize(() => (this.isBilling = false)))
        .subscribe();
    });
  }

  payCash() {
    this.isPaying = true;
    this.factureService
      .payCash(this.buildPayingCashRequest())
      .pipe(finalize(() => (this.isPaying = false)))
      .subscribe();
  }

  private buildPayingCashRequest(): IReglementEspeceRequest {
    const payCashRequest = {};
    if (this.reportingForm.get('societe').value) {
      payCashRequest['societeId'] = this.reportingForm.get('societe').value.id;
    }
    if (this.reportingForm.get('client').value) {
      payCashRequest['clientId'] = this.reportingForm.get('client').value.id;
    }
    if (this.reportingForm.get('produit').value) {
      payCashRequest['produitId'] = this.reportingForm.get('produit').value.id;
    }
    if (this.reportingForm.get('typeLivraison').value) {
      payCashRequest['typeLivraison'] = this.reportingForm.get('typeLivraison').value;
    }
    if (this.reportingForm.get('chantier').value) {
      payCashRequest['chantier'] = this.reportingForm.get('chantier').value;
    }
    if (this.reportingForm.get('dateDebut').value) {
      payCashRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    if (this.reportingForm.get('dateFin').value) {
      payCashRequest['dateFin'] = format(this.reportingForm.get('dateFin').value);
    }

    return payCashRequest;
  }

  private buildFindFacturesRequest(): any {
    const findFacturesRequest = {
      page: this.pageFactures,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    if (this.reportingForm.get('societe').value) {
      findFacturesRequest['societeId'] = this.reportingForm.get('societe').value.id;
    }
    if (this.reportingForm.get('client').value) {
      findFacturesRequest['clientId'] = this.reportingForm.get('client').value.id;
    }
    if (this.reportingForm.get('produit').value) {
      findFacturesRequest['produitId'] = this.reportingForm.get('produit').value.id;
    }
    if (this.reportingForm.get('typeLivraison').value) {
      findFacturesRequest['typeLivraison'] = this.reportingForm.get('typeLivraison').value;
    }
    if (this.reportingForm.get('facture').value) {
      findFacturesRequest['facture'] = this.reportingForm.get('facture').value;
    }
    if (this.reportingForm.get('chantier').value) {
      findFacturesRequest['chantier'] = this.reportingForm.get('chantier').value;
    }
    if (this.reportingForm.get('dateDebut').value) {
      findFacturesRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    if (this.reportingForm.get('dateFin').value) {
      findFacturesRequest['dateFin'] = format(this.reportingForm.get('dateFin').value);
    }
    if (this.reportingForm.get('regleEnEspece').value) {
      findFacturesRequest['regleEnEspece'] = this.reportingForm.get('regleEnEspece').value;
    }
    return findFacturesRequest;
  }

  protected paginateFactures(data: IFacture[], headers: HttpHeaders) {
    this.linksFactures = this.parseLinks.parse(headers.get('link'));
    this.totalItemsFactures = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.factures.push(data[i]);
    }
  }

  searchFactures() {
    this.isSearching = true;
    this.factureService.findFactures(this.buildFindFacturesRequest()).subscribe((res: HttpResponse<IFacture[]>) => {
      this.isSearching = false;
      this.paginateFactures(res.body, res.headers);
    });
  }

  searchReglements() {}
}
