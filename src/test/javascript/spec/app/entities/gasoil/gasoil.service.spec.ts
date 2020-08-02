import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { GasoilService } from 'app/entities/gasoil/gasoil.service';
import { IGasoil, Gasoil } from 'app/shared/model/gasoil.model';

describe('Service Tests', () => {
  describe('Gasoil Service', () => {
    let injector: TestBed;
    let service: GasoilService;
    let httpMock: HttpTestingController;
    let elemDefault: IGasoil;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GasoilService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Gasoil(0, 'AAAAAAA', 0, 'AAAAAAA', 0, 0, 0, 0, 0, 0);
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

      it('should create a Gasoil', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Gasoil(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Gasoil', () => {
        const returnedFromService = Object.assign(
          {
            societe: 'BBBBBB',
            numeroBonGasoil: 1,
            matricule: 'BBBBBB',
            quantiteEnLitre: 1,
            prixDuLitre: 1,
            prixTotalGasoil: 1,
            kilometrageInitial: 1,
            kilometrageFinal: 1,
            kilometrageParcouru: 1
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

      it('should return a list of Gasoil', () => {
        const returnedFromService = Object.assign(
          {
            societe: 'BBBBBB',
            numeroBonGasoil: 1,
            matricule: 'BBBBBB',
            quantiteEnLitre: 1,
            prixDuLitre: 1,
            prixTotalGasoil: 1,
            kilometrageInitial: 1,
            kilometrageFinal: 1,
            kilometrageParcouru: 1
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

      it('should delete a Gasoil', () => {
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
