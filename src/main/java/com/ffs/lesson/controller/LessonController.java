package com.ffs.lesson.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.lesson.LessonResultCode;
import com.ffs.lesson.application.LessonService;
import com.ffs.lesson.dto.*;
import com.ffs.lesson.dto.request.LessonCreateRequest;
import com.ffs.lesson.dto.request.LessonStatusUpdateRequest;
import com.ffs.lesson.dto.response.LessonDateResult;
import com.ffs.lesson.dto.request.LessonOnDateRequest;
import com.ffs.lesson.dto.response.LessonSearchResult;
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
                                                 @RequestBody LessonCreateRequest lessonCreateRequest) {
        lessonService.createLesson(principalDetails, lessonCreateRequest);

        return ResponseEntity.ok(LessonResultCode.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findLessons(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @RequestBody LessonOnDateRequest lessonOnDateRequest) {
        List<LessonInfo> lessons = lessonService.searchLessonOnDate(principalDetails, lessonOnDateRequest);
        if(lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        LessonSearchResult result = LessonSearchResult.builder().lessonInfoList(lessons).build();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/after/{date}")
    public ResponseEntity<Object> findLessonForAfterDate(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                           @PathVariable String date) {
        LessonSearchResult result = lessonService.findLessonAfterDate(principalDetails, date);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/month/{date}")
    public ResponseEntity<Object> findLessonDates(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                  @PathVariable String date) {
        LessonDateResult result = lessonService.findLessonDateList(principalDetails, date);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping
    public ResponseEntity<Object> updateLessonStatus(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @RequestBody LessonStatusUpdateRequest lessonStatusUpdateRequest) {
        lessonService.updateLessonState(principalDetails, lessonStatusUpdateRequest);

        return ResponseEntity.ok(LessonResultCode.OK);
    }

//    @GetMapping
//    public ResponseEntity<Object> findLessonById(@AuthenticationPrincipal PrincipalDetails principalDetails,
//                                                 @PathParam("id") Long lessonId) {
//        LessonInfo lessonInfo = lessonService.findLessonInfoById(principalDetails, lessonId);
//        LessonSearchResult result = LessonSearchResult.builder().lessonInfo(lessonInfo).build();
//
//        return ResponseEntity.ok().body(result);
//    }
}
