import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { TarifTransportDeleteDialogComponent } from 'app/entities/tarif-transport/tarif-transport-delete-dialog.component';
import { TarifTransportService } from 'app/entities/tarif-transport/tarif-transport.service';

describe('Component Tests', () => {
  describe('TarifTransport Management Delete Component', () => {
    let comp: TarifTransportDeleteDialogComponent;
    let fixture: ComponentFixture<TarifTransportDeleteDialogComponent>;
    let service: TarifTransportService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifTransportDeleteDialogComponent]
      })
        .overrideTemplate(TarifTransportDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifTransportDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifTransportService);
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
