package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "MEMBERSHIP")
public class Membership {

    @Id
    @Column(name = "MEMBER_ID")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "STOP_DATE")
    private LocalDate stopDate;

    @Column(name = "AVAILABLE_SLEEP_COUNT")
    private int AvailableSleepCount;
}
