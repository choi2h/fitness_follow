package com.ffs.lesson.application;

import com.ffs.auth.AuthUser;
import com.ffs.auth.PrincipalDetails;
import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.lesson.LessonResultCode;
import com.ffs.lesson.domain.Lesson;
import com.ffs.lesson.domain.LessonStatus;
import com.ffs.lesson.domain.repository.LessonRepository;
import com.ffs.lesson.dto.*;
import com.ffs.lesson.dto.request.RegisterLessonRequest;
import com.ffs.lesson.dto.request.UpdateLessonStatusRequest;
import com.ffs.lesson.dto.response.FindLessonDateResult;
import com.ffs.lesson.dto.response.FindLessonRequest;
import com.ffs.lesson.dto.response.FindLessonResult;
import com.ffs.user.Role;
import com.ffs.user.UserResultCode;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.member.domain.Member;
import com.ffs.user.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService {

    private final MemberRepository memberRepository;
    private final LessonRepository lessonRepository;
    private final LessonInfoMapper lessonInfoMapper;

    //TODO 레슨의 상태는 예약/취소/완료/결석 네가지로 관리될 수 있다.
    public void updateLessonState(PrincipalDetails userDetails, UpdateLessonStatusRequest request) {
        Lesson lesson = getLesson(request.getId());

        Long lessonEmployeeId = lesson.getEmployee().getId();
        if(!lessonEmployeeId.equals(userDetails.getId())) {
            throw new ServiceResultCodeException(LessonResultCode.NOT_HAVE_PERSIST_FOR_LESSON);
        }

        String updateStatus = request.getStatus();
        if(updateStatus.equals(LessonStatus.RESERVE.getName())) {
            lesson.reserve();
        } else if(updateStatus.equals(LessonStatus.CANCEL.getName())) {
            lesson.cancel();
        } else if(updateStatus.equals(LessonStatus.ABSENCE.getName())) {
            lesson.absence();
        } else if(updateStatus.equals(LessonStatus.COMPLETION.getName())) {
            lesson.complete();
        }

        lessonRepository.save(lesson);
    }

    //TODO 조건에 맞춰 자신의 레슨을 조회할 수 있다.
    // 날짜(월별, 일별, 설정 기간 내) / 상태 / 회원
    public List<LessonInfo> findLessonsForDate(PrincipalDetails userDetails, FindLessonRequest findLessonRequest) {
        log.debug("Receive find lessons for date. date={}", findLessonRequest.getDateTime());
        AuthUser authUser = userDetails.getAuthUser();

        Role role = authUser.getRole();

        List<Lesson> lessonList;
        LocalDateTime lessonDateTime = findLessonRequest.getDateTime();

        LocalDateTime startTime = lessonDateTime.toLocalDate().atStartOfDay();
        LocalDateTime endTime = startTime.plusDays(1).minusSeconds(1);

        Long userId = authUser.getUserId();
        if(role.equals(Role.MEMBER)) {
            lessonList = lessonRepository.findAllByMemberIdAndLessonDateTimeBetween(userId, startTime, endTime);
        } else {
            lessonList = lessonRepository.findAllByEmployeeIdAndLessonDateTimeBetweenOrderByLessonDateTime(userId, startTime, endTime);
        }

        log.debug("Found lessons for date. role={}, id={}, lessonLise={}", role, userId, lessonList.size());

        return lessonInfoMapper.convertLessonListToLessonInfoList(lessonList);
    }



    //TODO 신규 레슨을 등록할 수 있다.
    public void registerLesson(PrincipalDetails userDetails, RegisterLessonRequest registerLessonRequest) {
        log.debug("Receive request for register lesson.");
        Long memberId = registerLessonRequest.getMemberId();
        log.debug("Register lesson memberId={}, employeeId={}", memberId, userDetails.getId());
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_MEMBER);
        }

        Member member = memberOptional.get();
        Employee employee = member.getEmployee();
        if(employee == null ||
                !Objects.equals(employee.getId(), userDetails.getId())) {
            throw new ServiceResultCodeException(UserResultCode.NOT_HAVE_PERMISSION_FOR_MEMBER);
        }

        LocalDateTime lessonDateTime = registerLessonRequest.getLessonDateTime();

        Lesson lesson = Lesson.builder()
                .employee(employee)
                .member(member)
                .lessonDateTime(lessonDateTime)
                .price(BigDecimal.ONE)
                .build();

        lessonRepository.save(lesson);
    }

    //TODO 레슨의 상세 정보를 조회할 수 있다.
    public LessonInfo findLessonInfoById(PrincipalDetails userDetails, Long lessonId) {
        Lesson lesson = getLesson(lessonId);
        Long lessonEmployeeId = lesson.getEmployee().getId();
        if(!lessonEmployeeId.equals(userDetails.getId())) {
            throw new ServiceResultCodeException(LessonResultCode.NOT_HAVE_PERSIST_FOR_LESSON);
        }

        return lessonInfoMapper.convertLessonToLessonInfo(lesson);
    }

    public FindLessonResult findLessonAfterDate(PrincipalDetails userDetails, String date) {
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        LocalDateTime dateTime = LocalDate.of(year, month, day).atStartOfDay();

        long id = userDetails.getId();
        List<Lesson> lessons = lessonRepository
                .findLimit20ByEmployeeIdAndLessonDateTimeAfterOrderByLessonDateTime(id, dateTime);

        List<LessonInfos> lessonInfos = lessonInfoMapper.convertLessonListToLessonInfoMapByDate(lessons);
        return FindLessonResult.builder().lessonInfosList(lessonInfos).build();
    }

    public FindLessonDateResult findLessonDate(PrincipalDetails userDetails, String focusDate) {
        int year = Integer.parseInt(focusDate.substring(0,4));
        int month = Integer.parseInt(focusDate.substring(5, 7));
        LocalDate date = LocalDate.of(year, month, 1);

        LocalDateTime startDate = date.minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = date.plusMonths(2).withDayOfMonth(1).atStartOfDay().minusSeconds(1);

        long id = userDetails.getId();
        List<Lesson> lessons = lessonRepository
                .findAllByEmployeeIdAndLessonDateTimeBetweenOrderByLessonDateTime(id, startDate, endDate);

        List<String> lessonDates = new ArrayList<>();
        for(Lesson lesson : lessons) {
            String lessonDate = lesson.getLessonDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            lessonDates.add(lessonDate);
        }

        return FindLessonDateResult.builder().lessonDates(lessonDates).build();
    }

    //TODO 레슨 취소(삭제)가 가능하다
    public void deleteLesson(PrincipalDetails userDetails, Long lessonId) {
        Lesson lesson = getLesson(lessonId);
        Long lessonEmployeeId = lesson.getEmployee().getId();
        if(!lessonEmployeeId.equals(userDetails.getId())) {
            throw new ServiceResultCodeException(LessonResultCode.NOT_HAVE_PERSIST_FOR_LESSON);
        }

        lessonRepository.deleteById(lessonId);
    }

    private Lesson getLesson(Long id) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if(optionalLesson.isEmpty()) {
            throw new ServiceResultCodeException(LessonResultCode.NOT_EXIST_LESSON);
        }

        return optionalLesson.get();
    }
}
