import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TrajetDetailComponent } from 'app/entities/trajet/trajet-detail.component';
import { Trajet } from 'app/shared/model/trajet.model';

describe('Component Tests', () => {
  describe('Trajet Management Detail Component', () => {
    let comp: TrajetDetailComponent;
    let fixture: ComponentFixture<TrajetDetailComponent>;
    const route = ({ data: of({ trajet: new Trajet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TrajetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrajetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrajetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trajet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
