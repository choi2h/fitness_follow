package com.ffs.lesson.domain.repository;

import com.ffs.lesson.domain.LessonStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LessonStatusConverter implements AttributeConverter<LessonStatus, String> {

    @Override
    public String convertToDatabaseColumn(LessonStatus lessonStatus) {
        return lessonStatus.getName();
    }

    @Override
    public LessonStatus convertToEntityAttribute(String s) {
        return LessonStatus.getLessonStatus(s);
    }
}
