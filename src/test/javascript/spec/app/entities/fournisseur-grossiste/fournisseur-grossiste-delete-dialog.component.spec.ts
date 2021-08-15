import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { FournisseurGrossisteDeleteDialogComponent } from 'app/entities/fournisseur-grossiste/fournisseur-grossiste-delete-dialog.component';
import { FournisseurGrossisteService } from 'app/entities/fournisseur-grossiste/fournisseur-grossiste.service';

describe('Component Tests', () => {
  describe('FournisseurGrossiste Management Delete Component', () => {
    let comp: FournisseurGrossisteDeleteDialogComponent;
    let fixture: ComponentFixture<FournisseurGrossisteDeleteDialogComponent>;
    let service: FournisseurGrossisteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [FournisseurGrossisteDeleteDialogComponent]
      })
        .overrideTemplate(FournisseurGrossisteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FournisseurGrossisteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FournisseurGrossisteService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
