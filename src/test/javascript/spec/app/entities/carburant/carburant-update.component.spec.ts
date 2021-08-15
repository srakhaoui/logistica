import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { CarburantUpdateComponent } from 'app/entities/carburant/carburant-update.component';
import { CarburantService } from 'app/entities/carburant/carburant.service';
import { Carburant } from 'app/shared/model/carburant.model';

describe('Component Tests', () => {
  describe('Carburant Management Update Component', () => {
    let comp: CarburantUpdateComponent;
    let fixture: ComponentFixture<CarburantUpdateComponent>;
    let service: CarburantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [CarburantUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarburantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarburantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarburantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Carburant(123);
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
        const entity = new Carburant();
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
