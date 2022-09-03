package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AgregatTransfertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgregatTransfert.class);
        AgregatTransfert agregatTransfert1 = new AgregatTransfert();
        agregatTransfert1.setId(1L);
        AgregatTransfert agregatTransfert2 = new AgregatTransfert();
        agregatTransfert2.setId(agregatTransfert1.getId());
        assertThat(agregatTransfert1).isEqualTo(agregatTransfert2);
        agregatTransfert2.setId(2L);
        assertThat(agregatTransfert1).isNotEqualTo(agregatTransfert2);
        agregatTransfert1.setId(null);
        assertThat(agregatTransfert1).isNotEqualTo(agregatTransfert2);
    }
}
