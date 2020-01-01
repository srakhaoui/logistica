import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TransporteurDetailComponent } from 'app/entities/transporteur/transporteur-detail.component';
import { Transporteur } from 'app/shared/model/transporteur.model';

describe('Component Tests', () => {
  describe('Transporteur Management Detail Component', () => {
    let comp: TransporteurDetailComponent;
    let fixture: ComponentFixture<TransporteurDetailComponent>;
    const route = ({ data: of({ transporteur: new Transporteur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TransporteurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransporteurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransporteurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transporteur).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
