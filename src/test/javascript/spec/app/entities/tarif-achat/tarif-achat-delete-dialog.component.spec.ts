import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { TarifAchatDeleteDialogComponent } from 'app/entities/tarif-achat/tarif-achat-delete-dialog.component';
import { TarifAchatService } from 'app/entities/tarif-achat/tarif-achat.service';

describe('Component Tests', () => {
  describe('TarifAchat Management Delete Component', () => {
    let comp: TarifAchatDeleteDialogComponent;
    let fixture: ComponentFixture<TarifAchatDeleteDialogComponent>;
    let service: TarifAchatService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifAchatDeleteDialogComponent]
      })
        .overrideTemplate(TarifAchatDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifAchatDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifAchatService);
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
