package com.ffs.lesson.application;

import com.ffs.lesson.domain.Lesson;
import com.ffs.lesson.dto.LessonInfo;
import com.ffs.lesson.dto.LessonInfos;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LessonInfoMapper {

    protected LessonInfo convertLessonToLessonInfo(Lesson lesson) {
        return LessonInfo.builder()
                .id(lesson.getId())
                .memberId(lesson.getMemberId())
                .memberName(lesson.getMemberName())
                .employeeId(lesson.getEmployeeId())
                .employeeName(lesson.getEmployeeName())
                .lessonDateTime(lesson.getLessonDateTime())
                .status(lesson.getStatus().getName())
                .build();
    }

    protected List<LessonInfo> convertLessonListToLessonInfoList(List<Lesson> lessonList) {
        List<LessonInfo> lessonInfoList = new ArrayList<>();
        for(Lesson lesson : lessonList) {
            LessonInfo lessonInfo = convertLessonToLessonInfo(lesson);
            lessonInfoList.add(lessonInfo);
        }

        return lessonInfoList;
    }

    protected List<LessonInfos> convertLessonListToLessonInfoMapByDate(List<Lesson> lessonList) {
        Map<LocalDate, LessonInfos> lessonInfoMap = new HashMap<>();

        for(Lesson lesson : lessonList) {
            LocalDate date = lesson.getLessonDateTime().toLocalDate();
            LessonInfo lessonInfo = convertLessonToLessonInfo(lesson);

            if(lessonInfoMap.containsKey(date)) {
                lessonInfoMap.get(date).addLessonInfo(lessonInfo);
            } else {
                LessonInfos lessonInfos =
                        LessonInfos.builder().date(date).lessonInfo(lessonInfo).build();
                lessonInfoMap.put(date, lessonInfos);
            }
        }

        return new ArrayList<>(lessonInfoMap.values());

    }
}
