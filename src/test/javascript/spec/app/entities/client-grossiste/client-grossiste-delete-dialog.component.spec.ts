import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { ClientGrossisteDeleteDialogComponent } from 'app/entities/client-grossiste/client-grossiste-delete-dialog.component';
import { ClientGrossisteService } from 'app/entities/client-grossiste/client-grossiste.service';

describe('Component Tests', () => {
  describe('ClientGrossiste Management Delete Component', () => {
    let comp: ClientGrossisteDeleteDialogComponent;
    let fixture: ComponentFixture<ClientGrossisteDeleteDialogComponent>;
    let service: ClientGrossisteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [ClientGrossisteDeleteDialogComponent]
      })
        .overrideTemplate(ClientGrossisteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClientGrossisteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClientGrossisteService);
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
