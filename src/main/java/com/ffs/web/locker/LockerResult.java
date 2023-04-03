package com.ffs.web.locker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.domain.locker.entity.Locker;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LockerResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    private Locker locker;

    private List<Locker> lockerList;
}
