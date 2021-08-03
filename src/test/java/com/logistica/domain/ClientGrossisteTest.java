package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientGrossisteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientGrossiste.class);
        ClientGrossiste clientGrossiste1 = new ClientGrossiste();
        clientGrossiste1.setId(1L);
        ClientGrossiste clientGrossiste2 = new ClientGrossiste();
        clientGrossiste2.setId(clientGrossiste1.getId());
        assertThat(clientGrossiste1).isEqualTo(clientGrossiste2);
        clientGrossiste2.setId(2L);
        assertThat(clientGrossiste1).isNotEqualTo(clientGrossiste2);
        clientGrossiste1.setId(null);
        assertThat(clientGrossiste1).isNotEqualTo(clientGrossiste2);
    }
}
