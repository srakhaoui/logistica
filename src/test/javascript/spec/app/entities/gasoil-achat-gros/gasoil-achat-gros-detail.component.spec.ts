import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilAchatGrosDetailComponent } from 'app/entities/gasoil-achat-gros/gasoil-achat-gros-detail.component';
import { GasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

describe('Component Tests', () => {
  describe('GasoilAchatGros Management Detail Component', () => {
    let comp: GasoilAchatGrosDetailComponent;
    let fixture: ComponentFixture<GasoilAchatGrosDetailComponent>;
    const route = ({ data: of({ gasoilAchatGros: new GasoilAchatGros(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilAchatGrosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GasoilAchatGrosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GasoilAchatGrosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gasoilAchatGros).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
