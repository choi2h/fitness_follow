package com.ffs.membership.domain;

import com.ffs.user.domain.User;
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
    @Column(name = "MEMBERSHIP_ID")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "AVAILABLE_SLEEP_COUNT")
    private int availableSleepCount;

    @Builder
    public Membership(User user, LocalDate startDate, LocalDate endDate, int availableSleepCount){
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableSleepCount = availableSleepCount;
    }

    public void updateUseDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
