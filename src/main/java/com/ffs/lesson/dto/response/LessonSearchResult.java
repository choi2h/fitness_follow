package com.ffs.lesson.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.lesson.dto.LessonInfo;
import com.ffs.lesson.dto.LessonInfos;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonSearchResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    LessonInfo lessonInfo;

    List<LessonInfo> lessonInfoList;

    List<LessonInfos> lessonInfosList;

}
