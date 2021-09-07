import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GasoilAchatGrosService } from 'app/entities/gasoil-achat-gros/gasoil-achat-gros.service';
import { IGasoilAchatGros, GasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';
import { UniteGasoilGros } from 'app/shared/model/enumerations/unite-gasoil-gros.model';

describe('Service Tests', () => {
  describe('GasoilAchatGros Service', () => {
    let injector: TestBed;
    let service: GasoilAchatGrosService;
    let httpMock: HttpTestingController;
    let elemDefault: IGasoilAchatGros;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GasoilAchatGrosService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GasoilAchatGros(0, currentDate, 'AAAAAAA', 'AAAAAAA', 0, 0, UniteGasoilGros.LITRE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateReception: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a GasoilAchatGros', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateReception: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateReception: currentDate
          },
          returnedFromService
        );
        service
          .create(new GasoilAchatGros(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a GasoilAchatGros', () => {
        const returnedFromService = Object.assign(
          {
            dateReception: currentDate.format(DATE_FORMAT),
            numeroBonReception: 'BBBBBB',
            description: 'BBBBBB',
            quantity: 1,
            prixUnitaire: 1,
            uniteGasoilGros: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateReception: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of GasoilAchatGros', () => {
        const returnedFromService = Object.assign(
          {
            dateReception: currentDate.format(DATE_FORMAT),
            numeroBonReception: 'BBBBBB',
            description: 'BBBBBB',
            quantity: 1,
            prixUnitaire: 1,
            uniteGasoilGros: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateReception: currentDate
          },
          returnedFromService
        );
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

      it('should delete a GasoilAchatGros', () => {
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
