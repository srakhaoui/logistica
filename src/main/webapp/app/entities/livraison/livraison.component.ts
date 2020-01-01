import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivraison } from 'app/shared/model/livraison.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LivraisonService } from './livraison.service';
import { LivraisonDeleteDialogComponent } from './livraison-delete-dialog.component';

@Component({
  selector: 'jhi-livraison',
  templateUrl: './livraison.component.html'
})
export class LivraisonComponent implements OnInit, OnDestroy {
  livraisons: ILivraison[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected livraisonService: LivraisonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.livraisons = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.livraisonService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILivraison[]>) => this.paginateLivraisons(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.livraisons = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLivraisons();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILivraison) {
    return item.id;
  }

  registerChangeInLivraisons() {
    this.eventSubscriber = this.eventManager.subscribe('livraisonListModification', () => this.reset());
  }

  delete(livraison: ILivraison) {
    const modalRef = this.modalService.open(LivraisonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livraison = livraison;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLivraisons(data: ILivraison[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.livraisons.push(data[i]);
    }
  }
}
