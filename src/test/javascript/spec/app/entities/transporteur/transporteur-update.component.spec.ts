import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TransporteurUpdateComponent } from 'app/entities/transporteur/transporteur-update.component';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { Transporteur } from 'app/shared/model/transporteur.model';

describe('Component Tests', () => {
  describe('Transporteur Management Update Component', () => {
    let comp: TransporteurUpdateComponent;
    let fixture: ComponentFixture<TransporteurUpdateComponent>;
    let service: TransporteurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TransporteurUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransporteurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransporteurUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransporteurService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transporteur(123);
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
        const entity = new Transporteur();
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
