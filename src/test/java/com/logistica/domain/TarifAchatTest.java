package com.logistica.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.logistica.web.rest.TestUtil;

public class TarifAchatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifAchat.class);
        TarifAchat tarifAchat1 = new TarifAchat();
        tarifAchat1.setId(1L);
        TarifAchat tarifAchat2 = new TarifAchat();
        tarifAchat2.setId(tarifAchat1.getId());
        assertThat(tarifAchat1).isEqualTo(tarifAchat2);
        tarifAchat2.setId(2L);
        assertThat(tarifAchat1).isNotEqualTo(tarifAchat2);
        tarifAchat1.setId(null);
        assertThat(tarifAchat1).isNotEqualTo(tarifAchat2);
    }
}
