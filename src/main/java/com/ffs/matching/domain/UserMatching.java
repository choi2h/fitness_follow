package com.ffs.matching.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_MATCHING")
public class UserMatching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "FINISHED_AT")
    private LocalDateTime finishedAt;

    @Builder
    public UserMatching(Long employeeId, Long memberId, LocalDateTime createdAt) {
        this.employeeId = employeeId;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}
