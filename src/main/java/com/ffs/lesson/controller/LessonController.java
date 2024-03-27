package com.ffs.lesson.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.lesson.LessonResultCode;
import com.ffs.lesson.application.LessonService;
import com.ffs.lesson.dto.*;
import com.ffs.lesson.dto.request.LessonCreateRequest;
import com.ffs.lesson.dto.request.LessonSearchRequest;
import com.ffs.lesson.dto.request.LessonStatusUpdateRequest;
import com.ffs.lesson.dto.response.LessonDateResult;
import com.ffs.lesson.dto.request.LessonOnDateRequest;
import com.ffs.lesson.dto.response.LessonSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    /**
     * 수업 등록
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
    public ResponseEntity<Object> registerLesson(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                 @RequestBody LessonCreateRequest lessonCreateRequest) {
        lessonService.createLesson(principalDetails, lessonCreateRequest);

        return ResponseEntity.ok(LessonResultCode.OK);
    }

    /**
     * 해당 날짜의 레슨 조회
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<Object> findLessons(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @PathVariable String date) {
        List<LessonInfo> lessons = lessonService.searchLessonOnDate(principalDetails, date);
        if(lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        LessonSearchResult result = LessonSearchResult.builder().lessonInfoList(lessons).build();

        return ResponseEntity.ok().body(result);
    }


    /**
     * 검색 조건에 맞는 레슨 조회
     */
    @PostMapping("/filters")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
    public ResponseEntity<Object> findLessonByCondition(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                        @RequestBody LessonSearchRequest lessonSearchRequest) {
        LessonSearchResult result = lessonService.searchLessons(principalDetails, lessonSearchRequest);
//        if(lessons.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return ResponseEntity.ok().body(result);
    }


    /**
     * 날짜 이후의 레슨 조회
     */
    @GetMapping("/after/{date}")
    public ResponseEntity<Object> findLessonForAfterDate(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                           @PathVariable String date) {
        LessonSearchResult result = lessonService.findLessonAfterDate(principalDetails, date);

        return ResponseEntity.ok().body(result);
    }


    /**
     * 월간 레슨 조회
     */
    @GetMapping("/month/{date}")
    public ResponseEntity<Object> findLessonDates(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                  @PathVariable String date) {
        LessonDateResult result = lessonService.findLessonDateList(principalDetails, date);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 레슨 상태정보 수정
    */
    @PutMapping("/status")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
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
