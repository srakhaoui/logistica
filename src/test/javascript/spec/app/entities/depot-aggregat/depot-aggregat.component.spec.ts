import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LogisticaTestModule } from '../../../test.module';
import { DepotAggregatComponent } from 'app/entities/depot-aggregat/depot-aggregat.component';
import { DepotAggregatService } from 'app/entities/depot-aggregat/depot-aggregat.service';
import { DepotAggregat } from 'app/shared/model/depot-aggregat.model';

describe('Component Tests', () => {
  describe('DepotAggregat Management Component', () => {
    let comp: DepotAggregatComponent;
    let fixture: ComponentFixture<DepotAggregatComponent>;
    let service: DepotAggregatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [DepotAggregatComponent],
        providers: []
      })
        .overrideTemplate(DepotAggregatComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepotAggregatComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepotAggregatService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DepotAggregat(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.depotAggregats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
