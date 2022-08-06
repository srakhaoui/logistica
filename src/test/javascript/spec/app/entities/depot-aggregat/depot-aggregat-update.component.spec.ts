import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { DepotAggregatUpdateComponent } from 'app/entities/depot-aggregat/depot-aggregat-update.component';
import { DepotAggregatService } from 'app/entities/depot-aggregat/depot-aggregat.service';
import { DepotAggregat } from 'app/shared/model/depot-aggregat.model';

describe('Component Tests', () => {
  describe('DepotAggregat Management Update Component', () => {
    let comp: DepotAggregatUpdateComponent;
    let fixture: ComponentFixture<DepotAggregatUpdateComponent>;
    let service: DepotAggregatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [DepotAggregatUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepotAggregatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepotAggregatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepotAggregatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepotAggregat(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepotAggregat();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
