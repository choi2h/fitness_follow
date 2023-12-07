package com.ffs.lesson.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class LessonInfos {

    private LocalDate date;
    private List<LessonInfo> lessonInfos;

    @Builder
    public LessonInfos(LocalDate date, LessonInfo lessonInfo) {
        this.date = date;
        this.lessonInfos = new ArrayList<>();
        lessonInfos.add(lessonInfo);
    }

    public void addLessonInfo(LessonInfo lessonInfo) {
        this.lessonInfos.add(lessonInfo);
    }


}
