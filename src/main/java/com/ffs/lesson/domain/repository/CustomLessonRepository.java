package com.ffs.lesson.domain.repository;

import com.ffs.lesson.domain.Lesson;
import com.ffs.lesson.domain.LessonStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomLessonRepository {

    List<Lesson> findBySearchOptions(Long userId, String memberName, LessonStatus status,
                                     LocalDateTime startTime, LocalDateTime endTime);
}
