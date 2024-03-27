package com.ffs.lesson.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LessonInfo {

    private final Long id;
    private final Long memberId;
    private final String memberName;
    private final Long employeeId;
    private final String employeeName;
    private final LocalDateTime lessonDateTime;
    private final String status;

    @Builder
    public LessonInfo(Long id, Long memberId, String memberName, Long employeeId,
                  String employeeName, LocalDateTime lessonDateTime, String status) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.lessonDateTime = lessonDateTime;
        this.status = status;
    }

}
