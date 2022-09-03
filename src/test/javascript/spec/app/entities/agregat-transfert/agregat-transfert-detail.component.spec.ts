import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { AgregatTransfertDetailComponent } from 'app/entities/agregat-transfert/agregat-transfert-detail.component';
import { AgregatTransfert } from 'app/shared/model/agregat-transfert.model';

describe('Component Tests', () => {
  describe('AgregatTransfert Management Detail Component', () => {
    let comp: AgregatTransfertDetailComponent;
    let fixture: ComponentFixture<AgregatTransfertDetailComponent>;
    const route = ({ data: of({ agregatTransfert: new AgregatTransfert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [AgregatTransfertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgregatTransfertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgregatTransfertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agregatTransfert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
