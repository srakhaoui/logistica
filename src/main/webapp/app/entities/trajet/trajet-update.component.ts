import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITrajet, Trajet } from 'app/shared/model/trajet.model';
import { TrajetService } from './trajet.service';

@Component({
  selector: 'jhi-trajet-update',
  templateUrl: './trajet-update.component.html'
})
export class TrajetUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    depart: [null, [Validators.required]],
    destination: [null, [Validators.required]],
    commission: []
  });

  constructor(protected trajetService: TrajetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ trajet }) => {
      this.updateForm(trajet);
    });
  }

  updateForm(trajet: ITrajet) {
    this.editForm.patchValue({
      id: trajet.id,
      depart: trajet.depart,
      destination: trajet.destination,
      commission: trajet.commission
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const trajet = this.createFromForm();
    if (trajet.id !== undefined) {
      this.subscribeToSaveResponse(this.trajetService.update(trajet));
    } else {
      this.subscribeToSaveResponse(this.trajetService.create(trajet));
    }
  }

  private createFromForm(): ITrajet {
    return {
      ...new Trajet(),
      id: this.editForm.get(['id']).value,
      depart: this.editForm.get(['depart']).value,
      destination: this.editForm.get(['destination']).value,
      commission: this.editForm.get(['commission']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrajet>>) {
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
