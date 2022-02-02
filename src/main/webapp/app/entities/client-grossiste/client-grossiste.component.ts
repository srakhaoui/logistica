import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ClientGrossisteService } from './client-grossiste.service';
import { ClientGrossisteDeleteDialogComponent } from './client-grossiste-delete-dialog.component';

@Component({
  selector: 'jhi-client-grossiste',
  templateUrl: './client-grossiste.component.html'
})
export class ClientGrossisteComponent implements OnInit, OnDestroy {
  clientGrossistes: IClientGrossiste[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected clientGrossisteService: ClientGrossisteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.clientGrossistes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.clientGrossisteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IClientGrossiste[]>) => this.paginateClientGrossistes(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.clientGrossistes = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInClientGrossistes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IClientGrossiste) {
    return item.id;
  }

  registerChangeInClientGrossistes() {
    this.eventSubscriber = this.eventManager.subscribe('clientGrossisteListModification', () => this.reset());
  }

  delete(clientGrossiste: IClientGrossiste) {
    const modalRef = this.modalService.open(ClientGrossisteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.clientGrossiste = clientGrossiste;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateClientGrossistes(data: IClientGrossiste[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.clientGrossistes.push(data[i]);
    }
  }
}
