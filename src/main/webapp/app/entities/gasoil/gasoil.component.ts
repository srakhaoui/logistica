import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService } from 'ng-jhipster';
import { IGasoil } from 'app/shared/model/gasoil.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GasoilService } from './gasoil.service';
import { GasoilDeleteDialogComponent } from './gasoil-delete-dialog.component';

@Component({
  selector: 'jhi-gasoil',
  templateUrl: './gasoil.component.html',
  styleUrls: ['./gasoil.component.scss']
})
export class GasoilComponent implements OnInit, OnDestroy {
  gasoils: IGasoil[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected gasoilService: GasoilService,
    protected eventManager: JhiEventManager,
    protected jhiAlertService: JhiAlertService,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gasoils = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.gasoilService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGasoil[]>) => this.paginateGasoils(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.gasoils = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGasoils();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGasoil) {
    return item.id;
  }

  registerChangeInGasoils() {
    this.eventSubscriber = this.eventManager.subscribe('gasoilListModification', () => this.reset());
  }

  delete(gasoil: IGasoil) {
    const modalRef = this.modalService.open(GasoilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gasoil = gasoil;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGasoils(data: IGasoil[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.gasoils.push(data[i]);
    }
  }

  onFileUploaded(event){
      this.jhiAlertService.success(event['message'], {"bonsGasoil": event['bonsGasoil']}, null);
      this.loadAll();
  }
}
