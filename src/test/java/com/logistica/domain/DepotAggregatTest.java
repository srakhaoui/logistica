package com.logistica.domain;

import com.logistica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepotAggregatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepotAggregat.class);
        DepotAggregat depotAggregat1 = new DepotAggregat();
        depotAggregat1.setId(1L);
        DepotAggregat depotAggregat2 = new DepotAggregat();
        depotAggregat2.setId(depotAggregat1.getId());
        assertThat(depotAggregat1).isEqualTo(depotAggregat2);
        depotAggregat2.setId(2L);
        assertThat(depotAggregat1).isNotEqualTo(depotAggregat2);
        depotAggregat1.setId(null);
        assertThat(depotAggregat1).isNotEqualTo(depotAggregat2);
    }
}
