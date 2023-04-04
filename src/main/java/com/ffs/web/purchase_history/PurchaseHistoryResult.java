package com.ffs.web.purchase_history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.domain.purchaseHistory.entity.PurchaseHistory;
import lombok.Builder;

import java.util.List;


@Builder
@JsonInclude
public class PurchaseHistoryResult extends AbstractResponse {

    private PurchaseHistory purchaseHistory;

    private List<PurchaseHistory> purchaseHistoryList;
}
