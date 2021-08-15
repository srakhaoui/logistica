import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { CarburantDetailComponent } from 'app/entities/carburant/carburant-detail.component';
import { Carburant } from 'app/shared/model/carburant.model';

describe('Component Tests', () => {
  describe('Carburant Management Detail Component', () => {
    let comp: CarburantDetailComponent;
    let fixture: ComponentFixture<CarburantDetailComponent>;
    const route = ({ data: of({ carburant: new Carburant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [CarburantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarburantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarburantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carburant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
