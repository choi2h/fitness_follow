package com.ffs.membership.domain;

import com.ffs.user.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
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

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "AVAILABLE_SLEEP_COUNT")
    private int availableSleepCount;

    @Builder
    public Membership(Member member, LocalDate startDate, LocalDate endDate, int availableSleepCount){
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableSleepCount = availableSleepCount;
    }

    public void updateUseDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
