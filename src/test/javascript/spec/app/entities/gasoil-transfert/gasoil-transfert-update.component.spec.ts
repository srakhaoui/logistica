import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilTransfertUpdateComponent } from 'app/entities/gasoil-transfert/gasoil-transfert-update.component';
import { GasoilTransfertService } from 'app/entities/gasoil-transfert/gasoil-transfert.service';
import { GasoilTransfert } from 'app/shared/model/gasoil-transfert.model';

describe('Component Tests', () => {
  describe('GasoilTransfert Management Update Component', () => {
    let comp: GasoilTransfertUpdateComponent;
    let fixture: ComponentFixture<GasoilTransfertUpdateComponent>;
    let service: GasoilTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilTransfertUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GasoilTransfertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GasoilTransfertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GasoilTransfertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GasoilTransfert(123);
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
        const entity = new GasoilTransfert();
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
