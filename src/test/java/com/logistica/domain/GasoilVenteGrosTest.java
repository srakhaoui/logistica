package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GasoilVenteGrosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GasoilVenteGros.class);
        GasoilVenteGros gasoilVenteGros1 = new GasoilVenteGros();
        gasoilVenteGros1.setId(1L);
        GasoilVenteGros gasoilVenteGros2 = new GasoilVenteGros();
        gasoilVenteGros2.setId(gasoilVenteGros1.getId());
        assertThat(gasoilVenteGros1).isEqualTo(gasoilVenteGros2);
        gasoilVenteGros2.setId(2L);
        assertThat(gasoilVenteGros1).isNotEqualTo(gasoilVenteGros2);
        gasoilVenteGros1.setId(null);
        assertThat(gasoilVenteGros1).isNotEqualTo(gasoilVenteGros2);
    }
}
