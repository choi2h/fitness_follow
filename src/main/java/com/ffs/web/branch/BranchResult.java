package com.ffs.web.branch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.domain.branch.entity.Branch;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    private Long id;

    private Branch branch;

    private List<Branch> branchList;
}
