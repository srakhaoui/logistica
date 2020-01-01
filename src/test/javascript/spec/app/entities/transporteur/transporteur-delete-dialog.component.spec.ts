import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { TransporteurDeleteDialogComponent } from 'app/entities/transporteur/transporteur-delete-dialog.component';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';

describe('Component Tests', () => {
  describe('Transporteur Management Delete Component', () => {
    let comp: TransporteurDeleteDialogComponent;
    let fixture: ComponentFixture<TransporteurDeleteDialogComponent>;
    let service: TransporteurService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TransporteurDeleteDialogComponent]
      })
        .overrideTemplate(TransporteurDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransporteurDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransporteurService);
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
