import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { LogisticaTestModule } from '../../../test.module';
import { GasoilAchatGrosComponent } from 'app/entities/gasoil-achat-gros/gasoil-achat-gros.component';
import { GasoilAchatGrosService } from 'app/entities/gasoil-achat-gros/gasoil-achat-gros.service';
import { GasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

describe('Component Tests', () => {
  describe('GasoilAchatGros Management Component', () => {
    let comp: GasoilAchatGrosComponent;
    let fixture: ComponentFixture<GasoilAchatGrosComponent>;
    let service: GasoilAchatGrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LogisticaTestModule],
        declarations: [GasoilAchatGrosComponent],
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
        .overrideTemplate(GasoilAchatGrosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GasoilAchatGrosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GasoilAchatGrosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GasoilAchatGros(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gasoilAchatGros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GasoilAchatGros(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gasoilAchatGros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GasoilAchatGros(123)],
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
      expect(comp.gasoilAchatGros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
