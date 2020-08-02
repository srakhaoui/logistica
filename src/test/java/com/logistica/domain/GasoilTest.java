package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GasoilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gasoil.class);
        Gasoil gasoil1 = new Gasoil();
        gasoil1.setId(1L);
        Gasoil gasoil2 = new Gasoil();
        gasoil2.setId(gasoil1.getId());
        assertThat(gasoil1).isEqualTo(gasoil2);
        gasoil2.setId(2L);
        assertThat(gasoil1).isNotEqualTo(gasoil2);
        gasoil1.setId(null);
        assertThat(gasoil1).isNotEqualTo(gasoil2);
    }
}
