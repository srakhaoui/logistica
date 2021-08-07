import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilVenteGrosComponent } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros.component';
import { GasoilVenteGrosService } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros.service';
import { GasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';

describe('Component Tests', () => {
  describe('GasoilVenteGros Management Component', () => {
    let comp: GasoilVenteGrosComponent;
    let fixture: ComponentFixture<GasoilVenteGrosComponent>;
    let service: GasoilVenteGrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilVenteGrosComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(GasoilVenteGrosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GasoilVenteGrosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GasoilVenteGrosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GasoilVenteGros(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gasoilVenteGros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GasoilVenteGros(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gasoilVenteGros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GasoilVenteGros(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.gasoilVenteGros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
  });
});
