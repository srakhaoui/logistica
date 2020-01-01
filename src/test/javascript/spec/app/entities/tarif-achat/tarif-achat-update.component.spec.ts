import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TarifAchatUpdateComponent } from 'app/entities/tarif-achat/tarif-achat-update.component';
import { TarifAchatService } from 'app/entities/tarif-achat/tarif-achat.service';
import { TarifAchat } from 'app/shared/model/tarif-achat.model';

describe('Component Tests', () => {
  describe('TarifAchat Management Update Component', () => {
    let comp: TarifAchatUpdateComponent;
    let fixture: ComponentFixture<TarifAchatUpdateComponent>;
    let service: TarifAchatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifAchatUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TarifAchatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifAchatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifAchatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifAchat(123);
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
        const entity = new TarifAchat();
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
