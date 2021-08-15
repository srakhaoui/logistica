package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarburantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carburant.class);
        Carburant carburant1 = new Carburant();
        carburant1.setId(1L);
        Carburant carburant2 = new Carburant();
        carburant2.setId(carburant1.getId());
        assertThat(carburant1).isEqualTo(carburant2);
        carburant2.setId(2L);
        assertThat(carburant1).isNotEqualTo(carburant2);
        carburant1.setId(null);
        assertThat(carburant1).isNotEqualTo(carburant2);
    }
}
