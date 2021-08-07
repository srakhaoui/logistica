import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilVenteGrosDetailComponent } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros-detail.component';
import { GasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';

describe('Component Tests', () => {
  describe('GasoilVenteGros Management Detail Component', () => {
    let comp: GasoilVenteGrosDetailComponent;
    let fixture: ComponentFixture<GasoilVenteGrosDetailComponent>;
    const route = ({ data: of({ gasoilVenteGros: new GasoilVenteGros(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilVenteGrosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GasoilVenteGrosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GasoilVenteGrosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gasoilVenteGros).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
