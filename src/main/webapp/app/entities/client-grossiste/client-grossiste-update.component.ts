import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IClientGrossiste, ClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { ClientGrossisteService } from './client-grossiste.service';

@Component({
  selector: 'jhi-client-grossiste-update',
  templateUrl: './client-grossiste-update.component.html'
})
export class ClientGrossisteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required, Validators.minLength(1)]],
    adresse: [],
    telephone: [null, [Validators.pattern('^0[0-9]{9}$|\\s*')]]
  });

  constructor(
    protected clientGrossisteService: ClientGrossisteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ clientGrossiste }) => {
      this.updateForm(clientGrossiste);
    });
  }

  updateForm(clientGrossiste: IClientGrossiste) {
    this.editForm.patchValue({
      id: clientGrossiste.id,
      nom: clientGrossiste.nom,
      adresse: clientGrossiste.adresse,
      telephone: clientGrossiste.telephone
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const clientGrossiste = this.createFromForm();
    if (clientGrossiste.id !== undefined) {
      this.subscribeToSaveResponse(this.clientGrossisteService.update(clientGrossiste));
    } else {
      this.subscribeToSaveResponse(this.clientGrossisteService.create(clientGrossiste));
    }
  }

  private createFromForm(): IClientGrossiste {
    return {
      ...new ClientGrossiste(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      adresse: this.editForm.get(['adresse']).value,
      telephone: this.editForm.get(['telephone']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientGrossiste>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
