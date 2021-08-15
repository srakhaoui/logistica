import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { CarburantDeleteDialogComponent } from 'app/entities/carburant/carburant-delete-dialog.component';
import { CarburantService } from 'app/entities/carburant/carburant.service';

describe('Component Tests', () => {
  describe('Carburant Management Delete Component', () => {
    let comp: CarburantDeleteDialogComponent;
    let fixture: ComponentFixture<CarburantDeleteDialogComponent>;
    let service: CarburantService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [CarburantDeleteDialogComponent]
      })
        .overrideTemplate(CarburantDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarburantDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarburantService);
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
