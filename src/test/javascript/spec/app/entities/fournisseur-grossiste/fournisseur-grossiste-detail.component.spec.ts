import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { FournisseurGrossisteDetailComponent } from 'app/entities/fournisseur-grossiste/fournisseur-grossiste-detail.component';
import { FournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';

describe('Component Tests', () => {
  describe('FournisseurGrossiste Management Detail Component', () => {
    let comp: FournisseurGrossisteDetailComponent;
    let fixture: ComponentFixture<FournisseurGrossisteDetailComponent>;
    const route = ({ data: of({ fournisseurGrossiste: new FournisseurGrossiste(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [FournisseurGrossisteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FournisseurGrossisteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FournisseurGrossisteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fournisseurGrossiste).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
