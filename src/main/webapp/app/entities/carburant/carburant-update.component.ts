import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICarburant, Carburant } from 'app/shared/model/carburant.model';
import { CarburantService } from './carburant.service';

@Component({
  selector: 'jhi-carburant-update',
  templateUrl: './carburant-update.component.html'
})
export class CarburantUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.minLength(1)]],
    categorie: []
  });

  constructor(protected carburantService: CarburantService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carburant }) => {
      this.updateForm(carburant);
    });
  }

  updateForm(carburant: ICarburant) {
    this.editForm.patchValue({
      id: carburant.id,
      code: carburant.code,
      categorie: carburant.categorie
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carburant = this.createFromForm();
    if (carburant.id !== undefined) {
      this.subscribeToSaveResponse(this.carburantService.update(carburant));
    } else {
      this.subscribeToSaveResponse(this.carburantService.create(carburant));
    }
  }

  private createFromForm(): ICarburant {
    return {
      ...new Carburant(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      categorie: this.editForm.get(['categorie']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarburant>>) {
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
