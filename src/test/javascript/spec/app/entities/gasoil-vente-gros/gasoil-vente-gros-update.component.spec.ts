import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilVenteGrosUpdateComponent } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros-update.component';
import { GasoilVenteGrosService } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros.service';
import { GasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';

describe('Component Tests', () => {
  describe('GasoilVenteGros Management Update Component', () => {
    let comp: GasoilVenteGrosUpdateComponent;
    let fixture: ComponentFixture<GasoilVenteGrosUpdateComponent>;
    let service: GasoilVenteGrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilVenteGrosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GasoilVenteGrosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GasoilVenteGrosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GasoilVenteGrosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GasoilVenteGros(123);
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
        const entity = new GasoilVenteGros();
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
