import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifAchat } from 'app/shared/model/tarif-achat.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TarifAchatService } from './tarif-achat.service';
import { TarifAchatDeleteDialogComponent } from './tarif-achat-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-achat',
  templateUrl: './tarif-achat.component.html'
})
export class TarifAchatComponent implements OnInit, OnDestroy {
  tarifAchats: ITarifAchat[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected tarifAchatService: TarifAchatService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tarifAchats = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.tarifAchatService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITarifAchat[]>) => this.paginateTarifAchats(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.tarifAchats = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTarifAchats();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITarifAchat) {
    return item.id;
  }

  registerChangeInTarifAchats() {
    this.eventSubscriber = this.eventManager.subscribe('tarifAchatListModification', () => this.reset());
  }

  delete(tarifAchat: ITarifAchat) {
    const modalRef = this.modalService.open(TarifAchatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifAchat = tarifAchat;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTarifAchats(data: ITarifAchat[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.tarifAchats.push(data[i]);
    }
  }
}
