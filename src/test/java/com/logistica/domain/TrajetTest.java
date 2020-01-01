package com.logistica.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.logistica.web.rest.TestUtil;

public class TrajetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trajet.class);
        Trajet trajet1 = new Trajet();
        trajet1.setId(1L);
        Trajet trajet2 = new Trajet();
        trajet2.setId(trajet1.getId());
        assertThat(trajet1).isEqualTo(trajet2);
        trajet2.setId(2L);
        assertThat(trajet1).isNotEqualTo(trajet2);
        trajet1.setId(null);
        assertThat(trajet1).isNotEqualTo(trajet2);
    }
}
