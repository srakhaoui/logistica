import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilTransfertDetailComponent } from 'app/entities/gasoil-transfert/gasoil-transfert-detail.component';
import { GasoilTransfert } from 'app/shared/model/gasoil-transfert.model';

describe('Component Tests', () => {
  describe('GasoilTransfert Management Detail Component', () => {
    let comp: GasoilTransfertDetailComponent;
    let fixture: ComponentFixture<GasoilTransfertDetailComponent>;
    const route = ({ data: of({ gasoilTransfert: new GasoilTransfert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilTransfertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GasoilTransfertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GasoilTransfertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gasoilTransfert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
