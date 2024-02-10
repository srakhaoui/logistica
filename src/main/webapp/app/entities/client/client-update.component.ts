import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { SocieteService } from 'app/entities/societe/societe.service';
import { ISociete } from 'app/shared/model/societe.model';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html'
})
export class ClientUpdateComponent implements OnInit {
  isSaving: boolean;
  societes: ISociete[];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    adresse: [],
    telephone: [null, [Validators.pattern('^0[0-9]{9}$')]],
    societeFacturation: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clientService: ClientService,
    protected societeService: SocieteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);
    });
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(client: IClient) {
    this.editForm.patchValue({
      id: client.id,
      nom: client.nom,
      adresse: client.adresse,
      telephone: client.telephone,
      societeFacturation: client.societeFacturation
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      adresse: this.editForm.get(['adresse']).value,
      telephone: this.editForm.get(['telephone']).value,
      societeFacturation: this.editForm.get(['societeFacturation']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>) {
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
