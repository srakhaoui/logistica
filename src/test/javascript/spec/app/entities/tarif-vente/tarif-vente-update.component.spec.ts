import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TarifVenteUpdateComponent } from 'app/entities/tarif-vente/tarif-vente-update.component';
import { TarifVenteService } from 'app/entities/tarif-vente/tarif-vente.service';
import { TarifVente } from 'app/shared/model/tarif-vente.model';

describe('Component Tests', () => {
  describe('TarifVente Management Update Component', () => {
    let comp: TarifVenteUpdateComponent;
    let fixture: ComponentFixture<TarifVenteUpdateComponent>;
    let service: TarifVenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifVenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TarifVenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifVenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifVenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifVente(123);
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
        const entity = new TarifVente();
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
