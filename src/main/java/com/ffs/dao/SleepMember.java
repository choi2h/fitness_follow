package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "SLEEP_MEMBER")
public class SleepMember {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "STOP_DATE")
    private LocalDate stopDate;
}
