package com.ffs.lesson.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.lesson.LessonResultCode;
import com.ffs.lesson.application.LessonService;
import com.ffs.lesson.dto.*;
import com.ffs.lesson.dto.request.RegisterLessonRequest;
import com.ffs.lesson.dto.request.UpdateLessonStatusRequest;
import com.ffs.lesson.dto.response.FindLessonDateResult;
import com.ffs.lesson.dto.response.FindLessonRequest;
import com.ffs.lesson.dto.response.FindLessonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<LessonInfo> lessons = lessonService.findLessonsForDate(principalDetails, findLessonRequest);
        if(lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        FindLessonResult result = FindLessonResult.builder().lessonInfoList(lessons).build();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/after/{date}")
    public ResponseEntity<Object> findLessonForAfterDate(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                           @PathVariable String date) {
        FindLessonResult result = lessonService.findLessonAfterDate(principalDetails, date);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/month/{date}")
    public ResponseEntity<Object> findLessonDates(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                  @PathVariable String date) {
        FindLessonDateResult result = lessonService.findLessonDate(principalDetails, date);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping
    public ResponseEntity<Object> updateLessonStatus(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @RequestBody UpdateLessonStatusRequest request) {
        lessonService.updateLessonState(principalDetails, request);

        return ResponseEntity.ok(LessonResultCode.OK);
    }

//    @GetMapping
//    public ResponseEntity<Object> findLessonById(@AuthenticationPrincipal PrincipalDetails principalDetails,
//                                                 @PathParam("id") Long lessonId) {
//        LessonInfo lessonInfo = lessonService.findLessonInfoById(principalDetails, lessonId);
//        FindLessonResult result = FindLessonResult.builder().lessonInfo(lessonInfo).build();
//
//        return ResponseEntity.ok().body(result);
//    }
}
