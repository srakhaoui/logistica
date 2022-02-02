import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GasoilTransfertService } from 'app/entities/gasoil-transfert/gasoil-transfert.service';
import { IGasoilTransfert, GasoilTransfert } from 'app/shared/model/gasoil-transfert.model';

describe('Service Tests', () => {
  describe('GasoilTransfert Service', () => {
    let injector: TestBed;
    let service: GasoilTransfertService;
    let httpMock: HttpTestingController;
    let elemDefault: IGasoilTransfert;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GasoilTransfertService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GasoilTransfert(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            transfertDate: currentDate.format(DATE_FORMAT)
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

      it('should create a GasoilTransfert', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            transfertDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transfertDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new GasoilTransfert(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a GasoilTransfert', () => {
        const returnedFromService = Object.assign(
          {
            transfertDate: currentDate.format(DATE_FORMAT),
            quantite: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transfertDate: currentDate
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

      it('should return a list of GasoilTransfert', () => {
        const returnedFromService = Object.assign(
          {
            transfertDate: currentDate.format(DATE_FORMAT),
            quantite: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transfertDate: currentDate
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

      it('should delete a GasoilTransfert', () => {
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
