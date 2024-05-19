package com.logistica.service.dto;

import com.logistica.domain.ReglementEspece;

public class ReglementEspeceResponse {

    private long id;
    private double totalPrixHt;

    private boolean created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalPrixHt() {
        return totalPrixHt;
    }

    public void setTotalPrixHt(double totalPrixHt) {
        this.totalPrixHt = totalPrixHt;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public static ReglementEspeceResponse of(ReglementEspece reglementEspece) {
        ReglementEspeceResponse reglementResponse = new ReglementEspeceResponse();
        reglementResponse.setTotalPrixHt(reglementEspece.getMontant());
        reglementResponse.setId(reglementEspece.getId());
        reglementResponse.setCreated(true);
        return reglementResponse;
    }

    public static ReglementEspeceResponse noPayment() {
        ReglementEspeceResponse reglementResponse = new ReglementEspeceResponse();
        reglementResponse.setCreated(false);
        return reglementResponse;
    }
}
