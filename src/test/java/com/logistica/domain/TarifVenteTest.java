package com.logistica.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.logistica.web.rest.TestUtil;

public class TarifVenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifVente.class);
        TarifVente tarifVente1 = new TarifVente();
        tarifVente1.setId(1L);
        TarifVente tarifVente2 = new TarifVente();
        tarifVente2.setId(tarifVente1.getId());
        assertThat(tarifVente1).isEqualTo(tarifVente2);
        tarifVente2.setId(2L);
        assertThat(tarifVente1).isNotEqualTo(tarifVente2);
        tarifVente1.setId(null);
        assertThat(tarifVente1).isNotEqualTo(tarifVente2);
    }
}
