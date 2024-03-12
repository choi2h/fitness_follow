package com.ffs.lesson.domain;

import com.ffs.lesson.domain.repository.LessonStatusConverter;
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

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(name = "LESSON_DATETIME")
    private LocalDateTime lessonDateTime;

    @Column(name = "STATUS")
    @Convert(converter = LessonStatusConverter.class)
    private LessonStatus status; // (예약, 완료, 취소, 결석)

    @Column(name = "PRICE")
    private BigDecimal price;

    @Builder
    public Lesson(Long memberId, String memberName, Long employeeId, String employeeName,
                  LocalDateTime lessonDateTime, BigDecimal price) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
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
