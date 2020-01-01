import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { TarifAchatDetailComponent } from 'app/entities/tarif-achat/tarif-achat-detail.component';
import { TarifAchat } from 'app/shared/model/tarif-achat.model';

describe('Component Tests', () => {
  describe('TarifAchat Management Detail Component', () => {
    let comp: TarifAchatDetailComponent;
    let fixture: ComponentFixture<TarifAchatDetailComponent>;
    const route = ({ data: of({ tarifAchat: new TarifAchat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [TarifAchatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TarifAchatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifAchatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifAchat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
