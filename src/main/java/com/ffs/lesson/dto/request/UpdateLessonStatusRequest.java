package com.ffs.lesson.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLessonStatusRequest {

    private Long id;

    private String status;

}
