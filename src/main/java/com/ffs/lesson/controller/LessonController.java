package com.ffs.lesson.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.lesson.LessonResultCode;
import com.ffs.lesson.application.LessonService;
import com.ffs.lesson.dto.FindLessonRequest;
import com.ffs.lesson.dto.FindLessonResult;
import com.ffs.lesson.dto.RegisterLessonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<Object> registerLesson(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                 @RequestBody RegisterLessonRequest registerLessonRequest) {
        lessonService.registerLesson(principalDetails, registerLessonRequest);

        return ResponseEntity.ok(LessonResultCode.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findLessons(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @RequestBody FindLessonRequest findLessonRequest) {
        FindLessonResult result = lessonService.findLessonForDate(principalDetails, findLessonRequest);

        return ResponseEntity.ok().body(result);
    }


}
