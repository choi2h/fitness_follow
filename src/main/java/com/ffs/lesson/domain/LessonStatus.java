package com.ffs.lesson.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LessonStatus {
    RESERVE("예약"),
    COMPLETION("완료"),
    CANCEL("취소"),
    ABSENCE("결석");

    private String name;

    public static LessonStatus getLessonStatus(String name) {
        LessonStatus result = null;

        for(LessonStatus status : LessonStatus.values()) {
            if(name.equals(status.getName())){
                result = status;
                break;
            }
        }

        return result;
    }
}
