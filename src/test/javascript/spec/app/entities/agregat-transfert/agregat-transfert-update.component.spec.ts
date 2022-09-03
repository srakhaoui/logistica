import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { AgregatTransfertUpdateComponent } from 'app/entities/agregat-transfert/agregat-transfert-update.component';
import { AgregatTransfertService } from 'app/entities/agregat-transfert/agregat-transfert.service';
import { AgregatTransfert } from 'app/shared/model/agregat-transfert.model';

describe('Component Tests', () => {
  describe('AgregatTransfert Management Update Component', () => {
    let comp: AgregatTransfertUpdateComponent;
    let fixture: ComponentFixture<AgregatTransfertUpdateComponent>;
    let service: AgregatTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [AgregatTransfertUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AgregatTransfertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgregatTransfertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgregatTransfertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AgregatTransfert(123);
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
        const entity = new AgregatTransfert();
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
