package com.ffs.lesson.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindLessonDateResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    List<String> lessonDates;
}
