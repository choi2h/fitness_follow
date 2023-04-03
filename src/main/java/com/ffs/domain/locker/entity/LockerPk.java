package com.ffs.domain.locker.entity;

import com.ffs.domain.branch.entity.Branch;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
public class LockerPk implements Serializable {

    private static final long serialVersionUID = -532083507250373186L;

    @Column(name = "NUMBER")
    private int number;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @Builder
    public LockerPk(int number, Branch branch) {
        this.number = number;
        this.branch = branch;
    }
}
