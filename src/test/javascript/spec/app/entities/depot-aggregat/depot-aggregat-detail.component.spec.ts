import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { DepotAggregatDetailComponent } from 'app/entities/depot-aggregat/depot-aggregat-detail.component';
import { DepotAggregat } from 'app/shared/model/depot-aggregat.model';

describe('Component Tests', () => {
  describe('DepotAggregat Management Detail Component', () => {
    let comp: DepotAggregatDetailComponent;
    let fixture: ComponentFixture<DepotAggregatDetailComponent>;
    const route = ({ data: of({ depotAggregat: new DepotAggregat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [DepotAggregatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepotAggregatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepotAggregatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.depotAggregat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
