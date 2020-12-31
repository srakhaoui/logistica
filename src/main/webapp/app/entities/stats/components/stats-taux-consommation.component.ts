import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label, monkeyPatchChartJsLegend, monkeyPatchChartJsTooltip } from 'ng2-charts';
import { Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { StatsService } from '../stats.service';
import { IStatistiquesTauxConsommation } from 'app/shared/model/statistiques-taux-consommation.stats.model';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';
import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';

@Component({
  selector: 'jhi-stats-taux-consommation',
  templateUrl: './stats-taux-consommation.component.html',
  styleUrls: ['stats-taux-consommation.component.scss']
})
export class StatsTauxConsommationComponent implements OnInit, OnDestroy {

    societes: ISociete[];

    transporteurs$: Observable<ITransporteur[]>;
    transporteurInput$ = new Subject<string>();
    transporteursLoading:Boolean = false;

    statsForm = new FormGroup({
        societe: new FormControl(),
        transporteur: new FormControl(),
        dateDebut: new FormControl(),
        dateFin: new FormControl()
      });

    isSearching: Boolean = false;

    statsTauxConsommation: IStatistiquesTauxConsommation;

    public lineChartOptions: ChartOptions = { responsive: true };
    evolutionColors: Color[] = [ { borderColor: 'black', backgroundColor: 'rgba(255,0,0,0.3)' }];
    evolutionLegend: Boolean = true;
    evolutionChartType = 'bar';
    evolutionTauxConsommationChartType = 'line';

    evolutionLitrageChartData: ChartDataSets[] = [{data: [], label: "Litrage"}, {data: [], label: "Kilométrage"}];
    evolutionLitrageChartLabels: Label[] = [];

    evolutionTauxConsommationChartData: ChartDataSets[] = [{data: [], label: "Taux de consommation"}];
    evolutionTauxConsommationChartLabels: Label[] = [];

    classementTauxConsommationChartData: ChartDataSets[] = [{data: [], label: "Classement des matricules par taux de consommation"}];
    classementTauxConsommationChartLabels: Label[] = [];

    constructor(
      protected statsService: StatsService,
      protected societeService: SocieteService,
      protected transporteurService: TransporteurService,
      protected jhiAlertService: JhiAlertService,
      protected eventManager: JhiEventManager,
      protected modalService: NgbModal,
      protected parseLinks: JhiParseLinks,
      private fb: FormBuilder
    ) {
      this.initForm();
      monkeyPatchChartJsTooltip();
      monkeyPatchChartJsLegend();
    }

    private initForm(){
      const defaultDateDebut = moment(new Date()).startOf('month');
      defaultDateDebut.set("month", -12);
      this.statsForm.get('dateDebut').setValue(defaultDateDebut);
      const defaultDateFin = moment(new Date()).endOf('month');
      this.statsForm.get('dateFin').setValue(defaultDateFin);
    }

    ngOnInit() {
      this.loadAll();
    }

    ngOnDestroy() {
    }

    loadAll() {
      this.societeService
              .query()
              .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
      this.loadTransporteurs();
      this.search();
    }

    search(){
      this.isSearching = true;
      this.statsService
        .getStatistiquesTauxConsommation(this.buildStatsRequest())
        .subscribe((astatsTauxConsommation: IStatistiquesTauxConsommation) => {
          this.isSearching = false;
          this.statsTauxConsommation = astatsTauxConsommation;
          this.updateCharts();
        });
    }

    private updateCharts(){
      this.updateEvolutionCaChart();
    }

    private updateEvolutionCaChart(){
      this.evolutionLitrageChartData[0].data = this.statsTauxConsommation.evolutionLitrage.ordonnees;
      this.evolutionLitrageChartData[1].data = this.statsTauxConsommation.evolutionKilometrage.ordonnees;
      this.evolutionLitrageChartLabels = this.statsTauxConsommation.evolutionLitrage.abscisses;
      this.evolutionTauxConsommationChartData[0].data = this.statsTauxConsommation.evolutionTauxConsommation.ordonnees;
      this.evolutionTauxConsommationChartLabels = this.statsTauxConsommation.evolutionTauxConsommation.abscisses;
      this.classementTauxConsommationChartData[0].data = this.statsTauxConsommation.tauxConsommationParMatricule.ordonnees;
      this.classementTauxConsommationChartLabels = this.statsTauxConsommation.tauxConsommationParMatricule.abscisses;
    }

    private buildStatsRequest(): any {
      const statsRequest = {
      }
      if(this.statsForm.get('societe').value){
        statsRequest['societeId'] = this.statsForm.get('societe').value.id;
      }
      if(this.statsForm.get('transporteur').value){
        statsRequest['matricule'] = this.statsForm.get('transporteur').value.matricule;
      }
      if(this.statsForm.get('dateDebut').value){
        statsRequest['dateDebut'] = format(this.statsForm.get('dateDebut').value);
      }
      if(this.statsForm.get('dateFin').value){
        statsRequest['dateFin'] = format(this.statsForm.get('dateFin').value);
      }
      return statsRequest;
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

  public exportStats(){
    this.statsService.exportStatsRentabilite(this.buildStatsRequest());
  }
}
