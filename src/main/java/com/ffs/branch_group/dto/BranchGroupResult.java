package com.ffs.branch_group.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.branch_group.domain.BranchGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder @Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchGroupResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    private Long id;

    private BranchGroup branchGroup;

    private List<BranchGroup> branchGroupList;

}
