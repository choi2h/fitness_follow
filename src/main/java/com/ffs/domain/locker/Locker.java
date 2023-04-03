package com.ffs.domain.locker;

import com.ffs.domain.branch.Branch;
import com.ffs.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "LOCKER")
public class Locker {

    @Id
    @Column(name = "NUMBER")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @Column(name = "STATUS")
    private String status; // (사용안함, 사용중, 사용불가, 만기초과)

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "STOP_DATE")
    private LocalDate stopDate;

}
