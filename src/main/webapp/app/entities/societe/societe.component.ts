import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISociete } from 'app/shared/model/societe.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SocieteService } from './societe.service';
import { SocieteDeleteDialogComponent } from './societe-delete-dialog.component';

@Component({
  selector: 'jhi-societe',
  templateUrl: './societe.component.html'
})
export class SocieteComponent implements OnInit, OnDestroy {
  societes: ISociete[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected societeService: SocieteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.societes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.societeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISociete[]>) => this.paginateSocietes(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.societes = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSocietes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISociete) {
    return item.id;
  }

  registerChangeInSocietes() {
    this.eventSubscriber = this.eventManager.subscribe('societeListModification', () => this.reset());
  }

  delete(societe: ISociete) {
    const modalRef = this.modalService.open(SocieteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.societe = societe;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSocietes(data: ISociete[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.societes.push(data[i]);
    }
  }
}
