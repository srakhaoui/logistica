import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFournisseurGrossiste, FournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';
import { FournisseurGrossisteService } from './fournisseur-grossiste.service';

@Component({
  selector: 'jhi-fournisseur-grossiste-update',
  templateUrl: './fournisseur-grossiste-update.component.html'
})
export class FournisseurGrossisteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required, Validators.minLength(1)]],
    adresse: [],
    telephone: [null, [Validators.pattern('^0[0-9]{9}$|\\s*')]]
  });

  constructor(
    protected fournisseurGrossisteService: FournisseurGrossisteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fournisseurGrossiste }) => {
      this.updateForm(fournisseurGrossiste);
    });
  }

  updateForm(fournisseurGrossiste: IFournisseurGrossiste) {
    this.editForm.patchValue({
      id: fournisseurGrossiste.id,
      nom: fournisseurGrossiste.nom,
      adresse: fournisseurGrossiste.adresse,
      telephone: fournisseurGrossiste.telephone
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fournisseurGrossiste = this.createFromForm();
    if (fournisseurGrossiste.id !== undefined) {
      this.subscribeToSaveResponse(this.fournisseurGrossisteService.update(fournisseurGrossiste));
    } else {
      this.subscribeToSaveResponse(this.fournisseurGrossisteService.create(fournisseurGrossiste));
    }
  }

  private createFromForm(): IFournisseurGrossiste {
    return {
      ...new FournisseurGrossiste(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      adresse: this.editForm.get(['adresse']).value,
      telephone: this.editForm.get(['telephone']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFournisseurGrossiste>>) {
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
