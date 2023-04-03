package com.ffs.domain.locker.entity;

import com.ffs.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "LOCKER")
public class Locker {

    @EmbeddedId
    private LockerPk lockerPk;

    @Column(name = "STATUS")
    private String status; // (사용안함, 사용중, 사용불가, 만기초과)

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Builder
    public Locker(LockerPk lockerPk) {
        this.lockerPk = lockerPk;
        this.status = Status.CAN_USE.getText();
    }

    public void registerMember(Member member, LocalDate startDate, LocalDate endDate) {
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = Status.IN_USE.getText();
    }

    public void initLocker() {
        this.status = Status.CAN_USE.getText();
        this.member = null;
        this.startDate = null;
        this.endDate = null;
    }

    public void updateCanNotUseStatus() {
        this.status = Status.CAN_NOT_USE.getText();
    }

}
