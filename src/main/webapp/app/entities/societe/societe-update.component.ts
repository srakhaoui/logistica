import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISociete, Societe } from 'app/shared/model/societe.model';
import { SocieteService } from './societe.service';

@Component({
  selector: 'jhi-societe-update',
  templateUrl: './societe-update.component.html'
})
export class SocieteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]]
  });

  constructor(protected societeService: SocieteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ societe }) => {
      this.updateForm(societe);
    });
  }

  updateForm(societe: ISociete) {
    this.editForm.patchValue({
      id: societe.id,
      nom: societe.nom
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const societe = this.createFromForm();
    if (societe.id !== undefined) {
      this.subscribeToSaveResponse(this.societeService.update(societe));
    } else {
      this.subscribeToSaveResponse(this.societeService.create(societe));
    }
  }

  private createFromForm(): ISociete {
    return {
      ...new Societe(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISociete>>) {
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
