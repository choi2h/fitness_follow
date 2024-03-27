package com.ffs.purchase_history.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.purchase_history.domain.PurchaseHistory;
import lombok.Builder;

import java.util.List;


@Builder
@JsonInclude
public class PurchaseHistoryResult extends AbstractResponse {

    private PurchaseHistory purchaseHistory;

    private List<PurchaseHistory> purchaseHistoryList;
}
