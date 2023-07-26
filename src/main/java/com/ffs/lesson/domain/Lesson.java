package com.ffs.lesson.domain;

import com.ffs.employee.domain.Employee;
import com.ffs.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "LESSON")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LESSON_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @Column(name = "LESSON_DATETIME")
    private LocalDateTime lessonDateTime;

    @Column(name = "STATUS")
    private String status; // (예약, 완료, 취소, 결석)

    @Column(name = "PRICE")
    private BigDecimal price;

    @Builder
    public Lesson(Member member, Employee employee, LocalDateTime lessonDateTime, BigDecimal price) {
        this.member = member;
        this.employee = employee;
        this.lessonDateTime = lessonDateTime;
        this.price = price;
    }
}
