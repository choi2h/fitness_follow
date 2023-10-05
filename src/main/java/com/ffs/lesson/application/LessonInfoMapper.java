package com.ffs.lesson.application;

import com.ffs.lesson.domain.Lesson;
import com.ffs.lesson.dto.LessonInfo;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.member.domain.Member;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonInfoMapper {

    protected LessonInfo convertLessonToLessonInfo(Lesson lesson) {
        Member member = lesson.getMember();
        Employee employee = lesson.getEmployee();

        return LessonInfo.builder()
                .id(lesson.getId())
                .memberId(member.getId())
                .memberName(member.getName())
                .employeeId(employee.getId())
                .employeeName(employee.getName())
                .lessonDateTime(lesson.getLessonDateTime())
                .status(lesson.getStatus())
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
}
