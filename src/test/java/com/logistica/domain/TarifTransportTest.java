package com.logistica.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.logistica.web.rest.TestUtil;

public class TarifTransportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifTransport.class);
        TarifTransport tarifTransport1 = new TarifTransport();
        tarifTransport1.setId(1L);
        TarifTransport tarifTransport2 = new TarifTransport();
        tarifTransport2.setId(tarifTransport1.getId());
        assertThat(tarifTransport1).isEqualTo(tarifTransport2);
        tarifTransport2.setId(2L);
        assertThat(tarifTransport1).isNotEqualTo(tarifTransport2);
        tarifTransport1.setId(null);
        assertThat(tarifTransport1).isNotEqualTo(tarifTransport2);
    }
}
