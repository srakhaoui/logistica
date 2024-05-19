package com.logistica.service.dto;

import com.logistica.domain.enumeration.InvoiceStatus;

public class ValideFactureResponse {
    private InvoiceStatus invoiceStatus;

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public static ValideFactureResponse of(InvoiceStatus invoiceStatus){
        ValideFactureResponse valideFactureResponse = new ValideFactureResponse();
        valideFactureResponse.setInvoiceStatus(invoiceStatus);
        return valideFactureResponse;
    }
}
