package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepotTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Depot.class);
        Depot depot1 = new Depot();
        depot1.setId(1L);
        Depot depot2 = new Depot();
        depot2.setId(depot1.getId());
        assertThat(depot1).isEqualTo(depot2);
        depot2.setId(2L);
        assertThat(depot1).isNotEqualTo(depot2);
        depot1.setId(null);
        assertThat(depot1).isNotEqualTo(depot2);
    }
}
