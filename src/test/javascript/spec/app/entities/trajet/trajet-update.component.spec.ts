import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TrajetUpdateComponent } from 'app/entities/trajet/trajet-update.component';
import { TrajetService } from 'app/entities/trajet/trajet.service';
import { Trajet } from 'app/shared/model/trajet.model';

describe('Component Tests', () => {
  describe('Trajet Management Update Component', () => {
    let comp: TrajetUpdateComponent;
    let fixture: ComponentFixture<TrajetUpdateComponent>;
    let service: TrajetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TrajetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrajetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrajetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrajetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trajet(123);
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
        const entity = new Trajet();
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
