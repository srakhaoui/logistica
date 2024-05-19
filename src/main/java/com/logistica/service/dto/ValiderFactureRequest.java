package com.logistica.service.dto;

import com.logistica.domain.enumeration.InvoiceStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ValiderFactureRequest {
    @NotNull
    @PositiveOrZero
    private Long factureId;
    @NotNull
    private InvoiceStatus status;

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
