import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { ClientGrossisteUpdateComponent } from 'app/entities/client-grossiste/client-grossiste-update.component';
import { ClientGrossisteService } from 'app/entities/client-grossiste/client-grossiste.service';
import { ClientGrossiste } from 'app/shared/model/client-grossiste.model';

describe('Component Tests', () => {
  describe('ClientGrossiste Management Update Component', () => {
    let comp: ClientGrossisteUpdateComponent;
    let fixture: ComponentFixture<ClientGrossisteUpdateComponent>;
    let service: ClientGrossisteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [ClientGrossisteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClientGrossisteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClientGrossisteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClientGrossisteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClientGrossiste(123);
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
        const entity = new ClientGrossiste();
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
