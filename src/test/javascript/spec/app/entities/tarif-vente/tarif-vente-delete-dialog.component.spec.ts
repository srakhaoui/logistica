import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { TarifVenteDeleteDialogComponent } from 'app/entities/tarif-vente/tarif-vente-delete-dialog.component';
import { TarifVenteService } from 'app/entities/tarif-vente/tarif-vente.service';

describe('Component Tests', () => {
  describe('TarifVente Management Delete Component', () => {
    let comp: TarifVenteDeleteDialogComponent;
    let fixture: ComponentFixture<TarifVenteDeleteDialogComponent>;
    let service: TarifVenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifVenteDeleteDialogComponent]
      })
        .overrideTemplate(TarifVenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifVenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifVenteService);
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
