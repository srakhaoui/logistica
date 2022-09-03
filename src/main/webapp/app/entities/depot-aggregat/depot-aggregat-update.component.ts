import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDepotAggregat, DepotAggregat } from 'app/shared/model/depot-aggregat.model';
import { DepotAggregatService } from './depot-aggregat.service';

@Component({
  selector: 'jhi-depot-aggregat-update',
  templateUrl: './depot-aggregat-update.component.html'
})
export class DepotAggregatUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    stock: [null, [Validators.required, Validators.min(0)]],
    unite: [null, [Validators.required]],
    nom: [null, [Validators.required]]
  });

  constructor(protected depotAggregatService: DepotAggregatService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ depotAggregat }) => {
      this.updateForm(depotAggregat);
    });
  }

  updateForm(depotAggregat: IDepotAggregat) {
    this.editForm.patchValue({
      id: depotAggregat.id,
      stock: depotAggregat.stock,
      unite: depotAggregat.unite,
      nom: depotAggregat.nom
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const depotAggregat = this.createFromForm();
    if (depotAggregat.id !== undefined) {
      this.subscribeToSaveResponse(this.depotAggregatService.update(depotAggregat));
    } else {
      this.subscribeToSaveResponse(this.depotAggregatService.create(depotAggregat));
    }
  }

  private createFromForm(): IDepotAggregat {
    return {
      ...new DepotAggregat(),
      id: this.editForm.get(['id']).value,
      stock: this.editForm.get(['stock']).value,
      unite: this.editForm.get(['unite']).value,
      nom: this.editForm.get(['nom']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepotAggregat>>) {
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
