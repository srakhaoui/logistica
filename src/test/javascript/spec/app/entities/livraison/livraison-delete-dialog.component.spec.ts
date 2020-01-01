import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { LivraisonDeleteDialogComponent } from 'app/entities/livraison/livraison-delete-dialog.component';
import { LivraisonService } from 'app/entities/livraison/livraison.service';

describe('Component Tests', () => {
  describe('Livraison Management Delete Component', () => {
    let comp: LivraisonDeleteDialogComponent;
    let fixture: ComponentFixture<LivraisonDeleteDialogComponent>;
    let service: LivraisonService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [LivraisonDeleteDialogComponent]
      })
        .overrideTemplate(LivraisonDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivraisonDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivraisonService);
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
