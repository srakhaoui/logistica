import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepotAggregat } from 'app/shared/model/depot-aggregat.model';
import { DepotAggregatService } from './depot-aggregat.service';
import { DepotAggregatDeleteDialogComponent } from './depot-aggregat-delete-dialog.component';

@Component({
  selector: 'jhi-depot-aggregat',
  templateUrl: './depot-aggregat.component.html'
})
export class DepotAggregatComponent implements OnInit, OnDestroy {
  depotAggregats: IDepotAggregat[];
  eventSubscriber: Subscription;

  constructor(
    protected depotAggregatService: DepotAggregatService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.depotAggregatService.query().subscribe((res: HttpResponse<IDepotAggregat[]>) => {
      this.depotAggregats = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDepotAggregats();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDepotAggregat) {
    return item.id;
  }

  registerChangeInDepotAggregats() {
    this.eventSubscriber = this.eventManager.subscribe('depotAggregatListModification', () => this.loadAll());
  }

  delete(depotAggregat: IDepotAggregat) {
    const modalRef = this.modalService.open(DepotAggregatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.depotAggregat = depotAggregat;
  }
}
