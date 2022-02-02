import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GasoilVenteGrosService } from './gasoil-vente-gros.service';
import { GasoilVenteGrosDeleteDialogComponent } from './gasoil-vente-gros-delete-dialog.component';

@Component({
  selector: 'jhi-gasoil-vente-gros',
  templateUrl: './gasoil-vente-gros.component.html'
})
export class GasoilVenteGrosComponent implements OnInit, OnDestroy {
  gasoilVenteGros: IGasoilVenteGros[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected gasoilVenteGrosService: GasoilVenteGrosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gasoilVenteGros = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.gasoilVenteGrosService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGasoilVenteGros[]>) => this.paginateGasoilVenteGros(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.gasoilVenteGros = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGasoilVenteGros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGasoilVenteGros) {
    return item.id;
  }

  registerChangeInGasoilVenteGros() {
    this.eventSubscriber = this.eventManager.subscribe('gasoilVenteGrosListModification', () => this.reset());
  }

  delete(gasoilVenteGros: IGasoilVenteGros) {
    const modalRef = this.modalService.open(GasoilVenteGrosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gasoilVenteGros = gasoilVenteGros;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGasoilVenteGros(data: IGasoilVenteGros[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.gasoilVenteGros.push(data[i]);
    }
  }
}
