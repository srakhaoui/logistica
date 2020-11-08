package com.logistica.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_KM_INVALIDE = "error.km.invalide";
    public static final String ERR_PERIODE_INVALIDE = "error.periode.invalide";
    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_EXERCICE_COMPTABLE_LIVRAISON = "error.exercice.comptable.livraison";
    public static final String ERR_EXERCICE_COMPTABLE_COMMANDE = "error.exercice.comptable.commande";
    public static final String ERR_PRICING = "error.%s.pricingIssue";
    public static final String ERR_DUPLICATION = "error.item.duplicated";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String ERR_TRAJET_NOT_FOUND = "error.trajetNotFound";
    public static final String ERR_TARIF_ALREADY_EXISTS = "error.tarif.tarifAlreadyExists";
    public static final String ERR_COMMISSION_UNDEFINED = "error.trajetWithCommissionUndefined";
    public static final String ERR_MANY_COMMISSION = "error.trajetManyCommission";
    public static final String ERR_DATE_BON_CAISSE_FUTURE = "error.livraison.save.date.caisse.future";
    public static final String ERR_DATE_BON_LIVRAISON_FUTURE = "error.livraison.save.date.livraison.future";
    public static final String ERR_DATE_BON_CAISSE_SUP_DATE_LIVRAISON = "error.livraison.save.date.caisse.sup.date.livraison";
    public static final String ERR_DATE_BON_CAISSE_ANT_DATE_CMD = "error.livraison.save.date.caisse.ant.date.commande";
    public static final String ERR_DATE_BON_LIVRAISON_ANT_DATE_CMD = "error.livraison.save.date.livraison.ant.date.commande";
    public static final String ERR_BL_CLIENT_EXIST = "error.bl.client.exist";
    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");

    private ErrorConstants() {
    }
}
