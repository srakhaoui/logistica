import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LivraisonService } from 'app/entities/livraison/livraison.service';
import { ILivraison, Livraison } from 'app/shared/model/livraison.model';
import { Unite } from 'app/shared/model/enumerations/unite.model';
import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';

describe('Service Tests', () => {
  describe('Livraison Service', () => {
    let injector: TestBed;
    let service: LivraisonService;
    let httpMock: HttpTestingController;
    let elemDefault: ILivraison;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LivraisonService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Livraison(
        0,
        currentDate,
        0,
        0,
        currentDate,
        0,
        0,
        Unite.Tonne,
        0,
        0,
        Unite.Tonne,
        0,
        0,
        TypeLivraison.Transport,
        false,
        currentDate,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateBonCommande: currentDate.format(DATE_FORMAT),
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            dateBonCaisse: currentDate.format(DATE_FORMAT)
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

      it('should create a Livraison', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateBonCommande: currentDate.format(DATE_FORMAT),
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            dateBonCaisse: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateBonCommande: currentDate,
            dateBonLivraison: currentDate,
            dateBonCaisse: currentDate
          },
          returnedFromService
        );
        service
          .create(new Livraison(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Livraison', () => {
        const returnedFromService = Object.assign(
          {
            dateBonCommande: currentDate.format(DATE_FORMAT),
            numeroBonCommande: 1,
            numeroBonLivraison: 1,
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            numeroBonFournisseur: 1,
            quantiteVendue: 1,
            uniteVente: 'BBBBBB',
            prixTotalVente: 1,
            quantiteAchetee: 1,
            uniteAchat: 'BBBBBB',
            prixTotalAchat: 1,
            quantiteConvertie: 1,
            type: 'BBBBBB',
            facture: true,
            dateBonCaisse: currentDate.format(DATE_FORMAT),
            reparationDivers: 1,
            trax: 1,
            balance: 1,
            avance: 1,
            autoroute: 1,
            dernierEtat: 1,
            penaliteEse: 1,
            penaliteChfrs: 1,
            fraisEspece: 1,
            retenu: 1,
            totalComission: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBonCommande: currentDate,
            dateBonLivraison: currentDate,
            dateBonCaisse: currentDate
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

      it('should return a list of Livraison', () => {
        const returnedFromService = Object.assign(
          {
            dateBonCommande: currentDate.format(DATE_FORMAT),
            numeroBonCommande: 1,
            numeroBonLivraison: 1,
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            numeroBonFournisseur: 1,
            quantiteVendue: 1,
            uniteVente: 'BBBBBB',
            prixTotalVente: 1,
            quantiteAchetee: 1,
            uniteAchat: 'BBBBBB',
            prixTotalAchat: 1,
            quantiteConvertie: 1,
            type: 'BBBBBB',
            facture: true,
            dateBonCaisse: currentDate.format(DATE_FORMAT),
            reparationDivers: 1,
            trax: 1,
            balance: 1,
            avance: 1,
            autoroute: 1,
            dernierEtat: 1,
            penaliteEse: 1,
            penaliteChfrs: 1,
            fraisEspece: 1,
            retenu: 1,
            totalComission: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateBonCommande: currentDate,
            dateBonLivraison: currentDate,
            dateBonCaisse: currentDate
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

      it('should delete a Livraison', () => {
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
