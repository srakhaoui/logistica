import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilDetailComponent } from 'app/entities/gasoil/gasoil-detail.component';
import { Gasoil } from 'app/shared/model/gasoil.model';

describe('Component Tests', () => {
  describe('Gasoil Management Detail Component', () => {
    let comp: GasoilDetailComponent;
    let fixture: ComponentFixture<GasoilDetailComponent>;
    const route = ({ data: of({ gasoil: new Gasoil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GasoilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GasoilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gasoil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
