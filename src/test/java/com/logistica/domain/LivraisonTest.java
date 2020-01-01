package com.logistica.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.logistica.web.rest.TestUtil;

public class LivraisonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Livraison.class);
        Livraison livraison1 = new Livraison();
        livraison1.setId(1L);
        Livraison livraison2 = new Livraison();
        livraison2.setId(livraison1.getId());
        assertThat(livraison1).isEqualTo(livraison2);
        livraison2.setId(2L);
        assertThat(livraison1).isNotEqualTo(livraison2);
        livraison1.setId(null);
        assertThat(livraison1).isNotEqualTo(livraison2);
    }
}
