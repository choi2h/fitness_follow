package com.ffs.lesson.domain;

import com.ffs.lesson.domain.repository.LessonStatusConverter;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.member.domain.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @Column(name = "LESSON_DATETIME")
    private LocalDateTime lessonDateTime;

    @Column(name = "STATUS")
    @Convert(converter = LessonStatusConverter.class)
    private LessonStatus status; // (예약, 완료, 취소, 결석)

    @Column(name = "PRICE")
    private BigDecimal price;

    @Builder
    public Lesson(Member member, Employee employee, LocalDateTime lessonDateTime, BigDecimal price) {
        this.member = member;
        this.employee = employee;
        this.lessonDateTime = lessonDateTime;
        this.price = price;
        this.status = LessonStatus.RESERVE;
    }

    public void reserve() {
        this.status = LessonStatus.RESERVE;
    }

    public void cancel() {
        this.status = LessonStatus.CANCEL;
    }

    public void complete() {
        this.status = LessonStatus.COMPLETION;
    }

    public void absence() {
        this.status = LessonStatus.ABSENCE;
    }
}
