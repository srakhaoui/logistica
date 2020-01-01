import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TarifTransportDetailComponent } from 'app/entities/tarif-transport/tarif-transport-detail.component';
import { TarifTransport } from 'app/shared/model/tarif-transport.model';

describe('Component Tests', () => {
  describe('TarifTransport Management Detail Component', () => {
    let comp: TarifTransportDetailComponent;
    let fixture: ComponentFixture<TarifTransportDetailComponent>;
    const route = ({ data: of({ tarifTransport: new TarifTransport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifTransportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TarifTransportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifTransportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifTransport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
