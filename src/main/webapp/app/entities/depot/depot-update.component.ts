import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, Observable, of, concat } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap, startWith } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepot, Depot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';
import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { ClientGrossisteService } from 'app/entities/client-grossiste/client-grossiste.service';
import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';
import { FournisseurGrossisteService } from 'app/entities/fournisseur-grossiste/fournisseur-grossiste.service';

@Component({
  selector: 'jhi-depot-update',
  templateUrl: './depot-update.component.html'
})
export class DepotUpdateComponent implements OnInit {
  isSaving: boolean;

  clientsGrossistes$: Observable<IClientGrossiste[]>;
  clientsGrossistesInput$ = new Subject<string>();
  clientsGrossistesLoading:Boolean = false;

  fournisseurs$: Observable<IFournisseurGrossiste[]>;
  fournisseurInput$ = new Subject<string>();
  fournisseursLoading:Boolean = false;

  editForm = this.fb.group({
    id: [],
    stock: [null, [Validators.required, Validators.min(0)]],
    nom: [null, [Validators.required]],
    consommationInterne: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected depotService: DepotService,
    protected clientGrossisteService: ClientGrossisteService,
    protected fournisseurGrossisteService: FournisseurGrossisteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ depot }) => {
      this.updateForm(depot);
    });
    this.loadClientsGrossistes();
    this.loadFournisseursGrossistes();
  }

  updateForm(depot: IDepot) {
    this.editForm.patchValue({
      id: depot.id,
      stock: depot.stock,
      nom: depot.nom,
      consommationInterne: depot.consommationInterne
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const depot = this.createFromForm();
    if (depot.id !== undefined) {
      this.subscribeToSaveResponse(this.depotService.update(depot));
    } else {
      this.subscribeToSaveResponse(this.depotService.create(depot));
    }
  }

  private createFromForm(): IDepot {
    return {
      ...new Depot(),
      id: this.editForm.get(['id']).value,
      stock: this.editForm.get(['stock']).value,
      nom: this.editForm.get(['nom']).value,
      consommationInterne: this.editForm.get(['consommationInterne']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepot>>) {
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

  trackClientGrossisteById(index: number, item: IClientGrossiste) {
    return item.id;
  }

  trackFournisseurGrossisteById(index: number, item: IFournisseurGrossiste) {
    return item.id;
  }

  private loadClientsGrossistes(){
    this.clientsGrossistes$ = concat(
          of([]), // default items
          this.clientsGrossistesInput$.pipe(
              startWith(''),
              debounceTime(500),
              distinctUntilChanged(),
              tap(() => (this.clientsGrossistesLoading = true)),
              switchMap(nom =>
                  this.clientGrossisteService
                      .query({'nom.contains': nom})
                      .pipe(map((resp: HttpResponse<IClientGrossiste[]>) => resp.body), catchError(() => of([])))
              ),
              tap(() => (this.clientsGrossistesLoading = false))
          )
      );
}

  private loadFournisseursGrossistes(){
    this.fournisseurs$ = concat(
            of([]), // default items
            this.fournisseurInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.fournisseursLoading = true)),
                switchMap(nom =>
                    this.fournisseurGrossisteService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IFournisseurGrossiste[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.fournisseursLoading = false))
            )
        );
  }

}
