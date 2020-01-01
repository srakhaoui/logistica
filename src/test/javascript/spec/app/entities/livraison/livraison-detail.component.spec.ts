import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { LivraisonDetailComponent } from 'app/entities/livraison/livraison-detail.component';
import { Livraison } from 'app/shared/model/livraison.model';

describe('Component Tests', () => {
  describe('Livraison Management Detail Component', () => {
    let comp: LivraisonDetailComponent;
    let fixture: ComponentFixture<LivraisonDetailComponent>;
    const route = ({ data: of({ livraison: new Livraison(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [LivraisonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LivraisonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivraisonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.livraison).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
