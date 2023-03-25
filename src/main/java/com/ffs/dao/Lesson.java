package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "LESSON")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LESSON_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PT_ID")
    private Pt pt;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @Column(name = "LESSON_DATETIME")
    private LocalDateTime lessonDateTime;

    @Column(name = "STATUS")
    private String status; // (예약, 완료, 취소, 결석)

    @Column(name = "PRICE")
    private Long price;
}
