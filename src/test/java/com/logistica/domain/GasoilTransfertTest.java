package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GasoilTransfertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GasoilTransfert.class);
        GasoilTransfert gasoilTransfert1 = new GasoilTransfert();
        gasoilTransfert1.setId(1L);
        GasoilTransfert gasoilTransfert2 = new GasoilTransfert();
        gasoilTransfert2.setId(gasoilTransfert1.getId());
        assertThat(gasoilTransfert1).isEqualTo(gasoilTransfert2);
        gasoilTransfert2.setId(2L);
        assertThat(gasoilTransfert1).isNotEqualTo(gasoilTransfert2);
        gasoilTransfert1.setId(null);
        assertThat(gasoilTransfert1).isNotEqualTo(gasoilTransfert2);
    }
}
