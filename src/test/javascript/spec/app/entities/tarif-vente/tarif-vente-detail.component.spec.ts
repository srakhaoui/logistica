import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TarifVenteDetailComponent } from 'app/entities/tarif-vente/tarif-vente-detail.component';
import { TarifVente } from 'app/shared/model/tarif-vente.model';

describe('Component Tests', () => {
  describe('TarifVente Management Detail Component', () => {
    let comp: TarifVenteDetailComponent;
    let fixture: ComponentFixture<TarifVenteDetailComponent>;
    const route = ({ data: of({ tarifVente: new TarifVente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifVenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TarifVenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifVenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifVente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
