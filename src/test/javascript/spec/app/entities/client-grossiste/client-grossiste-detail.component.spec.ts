import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogisticaTestModule } from '../../../test.module';
import { ClientGrossisteDetailComponent } from 'app/entities/client-grossiste/client-grossiste-detail.component';
import { ClientGrossiste } from 'app/shared/model/client-grossiste.model';

describe('Component Tests', () => {
  describe('ClientGrossiste Management Detail Component', () => {
    let comp: ClientGrossisteDetailComponent;
    let fixture: ComponentFixture<ClientGrossisteDetailComponent>;
    const route = ({ data: of({ clientGrossiste: new ClientGrossiste(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [ClientGrossisteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClientGrossisteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClientGrossisteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clientGrossiste).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
