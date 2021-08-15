import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { FournisseurGrossisteUpdateComponent } from 'app/entities/fournisseur-grossiste/fournisseur-grossiste-update.component';
import { FournisseurGrossisteService } from 'app/entities/fournisseur-grossiste/fournisseur-grossiste.service';
import { FournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';

describe('Component Tests', () => {
  describe('FournisseurGrossiste Management Update Component', () => {
    let comp: FournisseurGrossisteUpdateComponent;
    let fixture: ComponentFixture<FournisseurGrossisteUpdateComponent>;
    let service: FournisseurGrossisteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [FournisseurGrossisteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FournisseurGrossisteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FournisseurGrossisteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FournisseurGrossisteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FournisseurGrossiste(123);
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
        const entity = new FournisseurGrossiste();
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
