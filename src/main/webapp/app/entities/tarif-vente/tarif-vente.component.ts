import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifVente } from 'app/shared/model/tarif-vente.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TarifVenteService } from './tarif-vente.service';
import { TarifVenteDeleteDialogComponent } from './tarif-vente-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-vente',
  templateUrl: './tarif-vente.component.html'
})
export class TarifVenteComponent implements OnInit, OnDestroy {
  tarifVentes: ITarifVente[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected tarifVenteService: TarifVenteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tarifVentes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.tarifVenteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITarifVente[]>) => this.paginateTarifVentes(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.tarifVentes = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTarifVentes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITarifVente) {
    return item.id;
  }

  registerChangeInTarifVentes() {
    this.eventSubscriber = this.eventManager.subscribe('tarifVenteListModification', () => this.reset());
  }

  delete(tarifVente: ITarifVente) {
    const modalRef = this.modalService.open(TarifVenteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifVente = tarifVente;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTarifVentes(data: ITarifVente[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.tarifVentes.push(data[i]);
    }
  }
}
