package com.ffs.lesson.domain.repository;

import com.ffs.lesson.domain.Lesson;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends Repository<Lesson, Long> {

    void save(Lesson lesson);

    List<Lesson> findAllByMemberIdAndLessonDateTimeBetween(Long memberId, LocalDateTime startTime, LocalDateTime endTime);
    List<Lesson> findAllByEmployeeIdAndLessonDateTimeBetween(Long memberId, LocalDateTime startTime, LocalDateTime endTime);
}
