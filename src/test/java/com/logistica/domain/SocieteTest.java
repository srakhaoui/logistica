package com.logistica.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.logistica.web.rest.TestUtil;

public class SocieteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Societe.class);
        Societe societe1 = new Societe();
        societe1.setId(1L);
        Societe societe2 = new Societe();
        societe2.setId(societe1.getId());
        assertThat(societe1).isEqualTo(societe2);
        societe2.setId(2L);
        assertThat(societe1).isNotEqualTo(societe2);
        societe1.setId(null);
        assertThat(societe1).isNotEqualTo(societe2);
    }
}
