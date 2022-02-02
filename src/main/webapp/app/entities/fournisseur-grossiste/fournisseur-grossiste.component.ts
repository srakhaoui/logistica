import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FournisseurGrossisteService } from './fournisseur-grossiste.service';
import { FournisseurGrossisteDeleteDialogComponent } from './fournisseur-grossiste-delete-dialog.component';

@Component({
  selector: 'jhi-fournisseur-grossiste',
  templateUrl: './fournisseur-grossiste.component.html'
})
export class FournisseurGrossisteComponent implements OnInit, OnDestroy {
  fournisseurGrossistes: IFournisseurGrossiste[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected fournisseurGrossisteService: FournisseurGrossisteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.fournisseurGrossistes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.fournisseurGrossisteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFournisseurGrossiste[]>) => this.paginateFournisseurGrossistes(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.fournisseurGrossistes = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInFournisseurGrossistes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFournisseurGrossiste) {
    return item.id;
  }

  registerChangeInFournisseurGrossistes() {
    this.eventSubscriber = this.eventManager.subscribe('fournisseurGrossisteListModification', () => this.reset());
  }

  delete(fournisseurGrossiste: IFournisseurGrossiste) {
    const modalRef = this.modalService.open(FournisseurGrossisteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fournisseurGrossiste = fournisseurGrossiste;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFournisseurGrossistes(data: IFournisseurGrossiste[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.fournisseurGrossistes.push(data[i]);
    }
  }
}
