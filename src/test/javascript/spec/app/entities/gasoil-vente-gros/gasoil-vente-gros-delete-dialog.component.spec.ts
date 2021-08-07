import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilVenteGrosDeleteDialogComponent } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros-delete-dialog.component';
import { GasoilVenteGrosService } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros.service';

describe('Component Tests', () => {
  describe('GasoilVenteGros Management Delete Component', () => {
    let comp: GasoilVenteGrosDeleteDialogComponent;
    let fixture: ComponentFixture<GasoilVenteGrosDeleteDialogComponent>;
    let service: GasoilVenteGrosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilVenteGrosDeleteDialogComponent]
      })
        .overrideTemplate(GasoilVenteGrosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GasoilVenteGrosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GasoilVenteGrosService);
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
