package com.logistica.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.logistica.web.rest.TestUtil;

public class TransporteurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transporteur.class);
        Transporteur transporteur1 = new Transporteur();
        transporteur1.setId(1L);
        Transporteur transporteur2 = new Transporteur();
        transporteur2.setId(transporteur1.getId());
        assertThat(transporteur1).isEqualTo(transporteur2);
        transporteur2.setId(2L);
        assertThat(transporteur1).isNotEqualTo(transporteur2);
        transporteur1.setId(null);
        assertThat(transporteur1).isNotEqualTo(transporteur2);
    }
}
