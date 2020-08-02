import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilUpdateComponent } from 'app/entities/gasoil/gasoil-update.component';
import { GasoilService } from 'app/entities/gasoil/gasoil.service';
import { Gasoil } from 'app/shared/model/gasoil.model';

describe('Component Tests', () => {
  describe('Gasoil Management Update Component', () => {
    let comp: GasoilUpdateComponent;
    let fixture: ComponentFixture<GasoilUpdateComponent>;
    let service: GasoilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GasoilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GasoilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GasoilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Gasoil(123);
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
        const entity = new Gasoil();
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
