package com.ffs.lesson.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindLessonResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    LessonInfo lessonInfo;

    List<LessonInfo> lessonInfoList;

}
