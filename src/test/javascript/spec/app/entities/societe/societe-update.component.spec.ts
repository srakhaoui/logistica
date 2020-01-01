import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { SocieteUpdateComponent } from 'app/entities/societe/societe-update.component';
import { SocieteService } from 'app/entities/societe/societe.service';
import { Societe } from 'app/shared/model/societe.model';

describe('Component Tests', () => {
  describe('Societe Management Update Component', () => {
    let comp: SocieteUpdateComponent;
    let fixture: ComponentFixture<SocieteUpdateComponent>;
    let service: SocieteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [SocieteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SocieteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SocieteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SocieteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Societe(123);
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
        const entity = new Societe();
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
