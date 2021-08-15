package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FournisseurGrossisteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FournisseurGrossiste.class);
        FournisseurGrossiste fournisseurGrossiste1 = new FournisseurGrossiste();
        fournisseurGrossiste1.setId(1L);
        FournisseurGrossiste fournisseurGrossiste2 = new FournisseurGrossiste();
        fournisseurGrossiste2.setId(fournisseurGrossiste1.getId());
        assertThat(fournisseurGrossiste1).isEqualTo(fournisseurGrossiste2);
        fournisseurGrossiste2.setId(2L);
        assertThat(fournisseurGrossiste1).isNotEqualTo(fournisseurGrossiste2);
        fournisseurGrossiste1.setId(null);
        assertThat(fournisseurGrossiste1).isNotEqualTo(fournisseurGrossiste2);
    }
}
