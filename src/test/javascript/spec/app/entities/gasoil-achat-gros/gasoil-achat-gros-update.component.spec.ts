import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilAchatGrosUpdateComponent } from 'app/entities/gasoil-achat-gros/gasoil-achat-gros-update.component';
import { GasoilAchatGrosService } from 'app/entities/gasoil-achat-gros/gasoil-achat-gros.service';
import { GasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

describe('Component Tests', () => {
  describe('GasoilAchatGros Management Update Component', () => {
    let comp: GasoilAchatGrosUpdateComponent;
    let fixture: ComponentFixture<GasoilAchatGrosUpdateComponent>;
    let service: GasoilAchatGrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilAchatGrosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GasoilAchatGrosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GasoilAchatGrosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GasoilAchatGrosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GasoilAchatGros(123);
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
        const entity = new GasoilAchatGros();
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
