import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { FormGroup, FormControl } from '@angular/forms';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ITrajet } from 'app/shared/model/trajet.model';
import { TrajetService } from 'app/entities/trajet/trajet.service';
import { StatsService } from '../stats.service';
import { IStatistiquesChiffreAffaire } from 'app/shared/model/statistiques-chiffre-affaire.stats.model';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';
import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';

@Component({
  selector: 'jhi-stats',
  templateUrl: './stats-chiffre-affaire.component.html'
})
export class StatsChiffreAffaireComponent implements OnInit, OnDestroy {

    societes: ISociete[];

    produits$: Observable<IProduit[]>;
    produitInput$ = new Subject<string>();
    produitsLoading:Boolean = false;

    transporteurs$: Observable<ITransporteur[]>;
    transporteurInput$ = new Subject<string>();
    transporteursLoading:Boolean = false;

    trajets$: Observable<ITrajet[]>;
    trajetInput$ = new Subject<string>();
    trajetsLoading:Boolean = false;

    statsForm = new FormGroup({
        societe: new FormControl(),
        trajet: new FormControl(),
        produit: new FormControl(),
        typeLivraison: new FormControl(),
        dateDebut: new FormControl(),
        dateFin: new FormControl()
      });

    isSearching: Boolean = false;

    statistiquesChiffreAffaire: IStatistiquesChiffreAffaire;

    constructor(
      protected statsService: StatsService,
      protected societeService: SocieteService,
      protected transporteurService: TransporteurService,
      protected produitService: ProduitService,
      protected trajetService: TrajetService,
      protected jhiAlertService: JhiAlertService,
      protected eventManager: JhiEventManager,
      protected modalService: NgbModal,
      protected parseLinks: JhiParseLinks
    ) {
      this.initForm();
    }

    private initForm(){
      const defaultDateDebut = moment(new Date()).startOf('month');
      this.statsForm.get('dateDebut').setValue(defaultDateDebut);
      const defaultDateFin = moment(new Date()).endOf('month');
      this.statsForm.get('dateFin').setValue(defaultDateFin);
    }

    loadAll() {
      this.societeService
              .query()
              .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
      this.loadTransporteurs();
      this.loadProduits();
      this.loadTrajets();
    }

    search(){
      this.isSearching = true;
      this.statsService
        .getStatistiquesChiffreAffaire(this.buildStatsRequest())
        .subscribe((statsChiffreAffaire: IStatistiquesChiffreAffaire) => {
          this.isSearching = false;
          this.statistiquesChiffreAffaire = statsChiffreAffaire;
          alert(statsChiffreAffaire);
        });
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

    private buildStatsRequest(): any {
      const statsRequest = {
        withTotalChiffreAffaire: true,
        withEvolutionChiffreAffaire: true,
        withRepartitionParTypeLivraison: true,
        withRepartitionParSocieteFacturation: true,
        withRepartitionParProduit: true,
        withRepartitionParTrajet: true,
        withRepartitionParMatricule: true
      }
      if(this.statsForm.get('societe').value){
        statsRequest['societeId'] = this.statsForm.get('societe').value.id;
      }
      if(this.statsForm.get('trajet').value){
        statsRequest['trajetId'] = this.statsForm.get('trajet').value.id;
      }
      if(this.statsForm.get('produit').value){
        statsRequest['produitId'] = this.statsForm.get('produit').value.id;
      }
      if(this.statsForm.get('typeLivraison').value){
        statsRequest['typeLivraison'] = this.statsForm.get('typeLivraison').value;
      }
      if(this.statsForm.get('matricule').value){
        statsRequest['matricule'] = this.statsForm.get('matricule').value;
      }
      if(this.statsForm.get('dateDebut').value){
        statsRequest['dateDebut'] = format(this.statsForm.get('dateDebut').value);
      }
      if(this.statsForm.get('dateFin').value){
        statsRequest['dateFin'] = format(this.statsForm.get('dateFin').value);
      }
      return statsRequest;
    }

    ngOnInit() {
      this.loadAll();
    }

    ngOnDestroy() {
    }

    trackSocieteById(index: number, item: ISociete) {
      return item.id;
    }

    protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
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
