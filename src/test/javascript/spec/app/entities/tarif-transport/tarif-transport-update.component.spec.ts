import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TarifTransportUpdateComponent } from 'app/entities/tarif-transport/tarif-transport-update.component';
import { TarifTransportService } from 'app/entities/tarif-transport/tarif-transport.service';
import { TarifTransport } from 'app/shared/model/tarif-transport.model';

describe('Component Tests', () => {
  describe('TarifTransport Management Update Component', () => {
    let comp: TarifTransportUpdateComponent;
    let fixture: ComponentFixture<TarifTransportUpdateComponent>;
    let service: TarifTransportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifTransportUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TarifTransportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifTransportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifTransportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifTransport(123);
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
        const entity = new TarifTransport();
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
