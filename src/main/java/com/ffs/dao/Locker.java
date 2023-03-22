package com.ffs.dao;

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
    private Long number;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "STATUS")
    private String status; // (사용안함, 사용중, 사용불가, 만기초과)

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "STOP_DATE")
    private LocalDate stopDate;

}
