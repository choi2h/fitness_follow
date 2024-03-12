package com.ffs.lesson.domain.repository;

import com.ffs.lesson.domain.Lesson;
import com.ffs.lesson.domain.LessonStatus;
import com.ffs.lesson.domain.QLesson;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomLessonRepositoryImpl implements CustomLessonRepository{

    private final JPAQueryFactory queryFactory;
    QLesson lesson = QLesson.lesson;

    @Override
    public List<Lesson> findBySearchOptions(Long userId, String memberName,
                                            LessonStatus status, LocalDateTime startTime, LocalDateTime endTime) {
        return queryFactory
                .selectFrom(lesson)
                .where(eqName(memberName),
                        eqStatus(status),
                        eqDateTime(startTime, endTime),
                        eqEmployeeId(userId)
                        )
                .fetch();
    }

    private BooleanExpression eqName(String name) {
        if (name == null) {
            return null;
        }
        return lesson.memberName.eq(name);
    }

    private BooleanExpression eqStatus(LessonStatus status) {
        if (status == null) {
            return null;
        }
        return lesson.status.eq(status);
    }

    private BooleanExpression eqDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return null;
        }
        return lesson.lessonDateTime.between(startTime, endTime);
    }

    private BooleanExpression eqEmployeeId(Long employeeId) {
        return lesson.employeeId.eq(employeeId);
    }
}
