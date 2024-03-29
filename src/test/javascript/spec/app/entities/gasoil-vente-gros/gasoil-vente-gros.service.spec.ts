import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { GasoilVenteGrosService } from 'app/entities/gasoil-vente-gros/gasoil-vente-gros.service';
import { IGasoilVenteGros, GasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';
import { UniteGasoilGros } from 'app/shared/model/enumerations/unite-gasoil-gros.model';

describe('Service Tests', () => {
  describe('GasoilVenteGros Service', () => {
    let injector: TestBed;
    let service: GasoilVenteGrosService;
    let httpMock: HttpTestingController;
    let elemDefault: IGasoilVenteGros;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GasoilVenteGrosService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new GasoilVenteGros(0, 0, 0, 0, 0, 0, UniteGasoilGros.LITRE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a GasoilVenteGros', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new GasoilVenteGros(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a GasoilVenteGros', () => {
        const returnedFromService = Object.assign(
          {
            prixVenteUnitaire: 1,
            quantite: 1,
            prixVenteTotal: 1,
            margeGlobale: 1,
            tauxMarge: 1,
            uniteGasoilGros: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of GasoilVenteGros', () => {
        const returnedFromService = Object.assign(
          {
            prixVenteUnitaire: 1,
            quantite: 1,
            prixVenteTotal: 1,
            margeGlobale: 1,
            tauxMarge: 1,
            uniteGasoilGros: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GasoilVenteGros', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
