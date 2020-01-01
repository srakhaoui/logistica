import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITransporteur, Transporteur } from 'app/shared/model/transporteur.model';
import { TransporteurService } from './transporteur.service';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';

@Component({
  selector: 'jhi-transporteur-update',
  templateUrl: './transporteur-update.component.html'
})
export class TransporteurUpdateComponent implements OnInit {
  isSaving: boolean;

  societes: ISociete[];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    telephone: [null, [Validators.pattern('^0[0-9]{9}$')]],
    matricule: [null, [Validators.required]],
    proprietaire: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transporteurService: TransporteurService,
    protected societeService: SocieteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transporteur }) => {
      this.updateForm(transporteur);
    });
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transporteur: ITransporteur) {
    this.editForm.patchValue({
      id: transporteur.id,
      nom: transporteur.nom,
      prenom: transporteur.prenom,
      telephone: transporteur.telephone,
      matricule: transporteur.matricule,
      proprietaire: transporteur.proprietaire
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transporteur = this.createFromForm();
    if (transporteur.id !== undefined) {
      this.subscribeToSaveResponse(this.transporteurService.update(transporteur));
    } else {
      this.subscribeToSaveResponse(this.transporteurService.create(transporteur));
    }
  }

  private createFromForm(): ITransporteur {
    return {
      ...new Transporteur(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      prenom: this.editForm.get(['prenom']).value,
      telephone: this.editForm.get(['telephone']).value,
      matricule: this.editForm.get(['matricule']).value,
      proprietaire: this.editForm.get(['proprietaire']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransporteur>>) {
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

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
  }
}
