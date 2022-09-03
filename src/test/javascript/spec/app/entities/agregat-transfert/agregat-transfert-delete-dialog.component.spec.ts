import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { AgregatTransfertDeleteDialogComponent } from 'app/entities/agregat-transfert/agregat-transfert-delete-dialog.component';
import { AgregatTransfertService } from 'app/entities/agregat-transfert/agregat-transfert.service';

describe('Component Tests', () => {
  describe('AgregatTransfert Management Delete Component', () => {
    let comp: AgregatTransfertDeleteDialogComponent;
    let fixture: ComponentFixture<AgregatTransfertDeleteDialogComponent>;
    let service: AgregatTransfertService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [AgregatTransfertDeleteDialogComponent]
      })
        .overrideTemplate(AgregatTransfertDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgregatTransfertDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgregatTransfertService);
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
