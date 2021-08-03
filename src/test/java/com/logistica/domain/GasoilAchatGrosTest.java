package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GasoilAchatGrosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GasoilAchatGros.class);
        GasoilAchatGros gasoilAchatGros1 = new GasoilAchatGros();
        gasoilAchatGros1.setId(1L);
        GasoilAchatGros gasoilAchatGros2 = new GasoilAchatGros();
        gasoilAchatGros2.setId(gasoilAchatGros1.getId());
        assertThat(gasoilAchatGros1).isEqualTo(gasoilAchatGros2);
        gasoilAchatGros2.setId(2L);
        assertThat(gasoilAchatGros1).isNotEqualTo(gasoilAchatGros2);
        gasoilAchatGros1.setId(null);
        assertThat(gasoilAchatGros1).isNotEqualTo(gasoilAchatGros2);
    }
}
