import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { DepotAggregatDeleteDialogComponent } from 'app/entities/depot-aggregat/depot-aggregat-delete-dialog.component';
import { DepotAggregatService } from 'app/entities/depot-aggregat/depot-aggregat.service';

describe('Component Tests', () => {
  describe('DepotAggregat Management Delete Component', () => {
    let comp: DepotAggregatDeleteDialogComponent;
    let fixture: ComponentFixture<DepotAggregatDeleteDialogComponent>;
    let service: DepotAggregatService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [DepotAggregatDeleteDialogComponent]
      })
        .overrideTemplate(DepotAggregatDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepotAggregatDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepotAggregatService);
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
