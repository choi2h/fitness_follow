package com.ffs.lesson.application;

import com.ffs.auth.AuthUser;
import com.ffs.auth.PrincipalDetails;
import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.lesson.LessonResultCode;
import com.ffs.lesson.domain.Lesson;
import com.ffs.lesson.domain.LessonStatus;
import com.ffs.lesson.domain.repository.CustomLessonRepository;
import com.ffs.lesson.domain.repository.LessonRepository;
import com.ffs.lesson.dto.LessonInfo;
import com.ffs.lesson.dto.LessonInfos;
import com.ffs.lesson.dto.request.LessonCreateRequest;
import com.ffs.lesson.dto.request.LessonOnDateRequest;
import com.ffs.lesson.dto.request.LessonSearchRequest;
import com.ffs.lesson.dto.request.LessonStatusUpdateRequest;
import com.ffs.lesson.dto.response.LessonDateResult;
import com.ffs.lesson.dto.response.LessonSearchResult;
import com.ffs.matching.domain.UserMatching;
import com.ffs.matching.domain.repository.UserMatchingRepository;
import com.ffs.user.UserResultCode;
import com.ffs.user.domain.Role;
import com.ffs.user.domain.User;
import com.ffs.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final LessonInfoMapper lessonInfoMapper;
    private final CustomLessonRepository customLessonRepository;
    private final UserMatchingRepository userMatchingRepository;

    // 레슨의 상태는 예약/취소/완료/결석 네가지로 관리될 수 있다.
    public void updateLessonState(PrincipalDetails userDetails, LessonStatusUpdateRequest request) {
        Lesson lesson = getLesson(request.getId());

        Long lessonEmployeeId = lesson.getEmployeeId();
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

    // 조건에 맞춰 자신의 레슨을 조회할 수 있다.
    // 날짜(월별, 일별, 설정 기간 내) / 상태 / 회원
    public LessonSearchResult searchLessons(PrincipalDetails userDetails, LessonSearchRequest request) {
        log.debug("Receive search lessons request. memberName={}, status={}, date[{}-{}]",
                request.getMemberName(), request.getStatus(), request.getStartDateTime(), request.getEndDateTime());

        AuthUser authUser = userDetails.getAuthUser();
        Long userId = authUser.getUserId();

        String memberName = request.getMemberName();
        LessonStatus status = null;
        if(request.getStatus() != null) {
            status = LessonStatus.getLessonStatus(request.getStatus());
        }
        LocalDateTime startDateTime = request.getStartDateTime();
        LocalDateTime endDateTime = request.getEndDateTime();

        List<Lesson> lessons = customLessonRepository
                .findBySearchOptions(userId, memberName, status, startDateTime, endDateTime);

        List<LessonInfos> lessonInfos = lessonInfoMapper.convertLessonListToLessonInfoMapByDate(lessons);
        return LessonSearchResult.builder().lessonInfosList(lessonInfos).build();
    }

    // 요청 날짜에 해당하는 레슨 정보들을 조회한다.
    public List<LessonInfo> searchLessonOnDate(PrincipalDetails userDetails, LessonOnDateRequest request) {
        log.debug("Receive find lessons for date. date={}", request.getDateTime());
        AuthUser authUser = userDetails.getAuthUser();

        Role role = authUser.getRole();

        List<Lesson> lessonList;
        LocalDateTime lessonDateTime = request.getDateTime();

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

    // 신규 레슨을 등록할 수 있다.
    public void createLesson(PrincipalDetails userDetails, LessonCreateRequest request) {
        Long memberId = request.getMemberId();
        Long employeeId = userDetails.getId();

        log.debug("Register lesson memberId={}, employeeId={}", memberId, userDetails.getId());

        Optional<UserMatching> userMatchingOptional =
                userMatchingRepository.findByMemberIdAndEmployeeId(memberId, employeeId);

        if(userMatchingOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_HAVE_PERMISSION_FOR_USER);
        }

        UserMatching userMatching = userMatchingOptional.get();
        LocalDateTime current = LocalDateTime.now();
        if(!userMatching.getCreatedAt().isBefore(current) || !userMatching.getFinishedAt().isAfter(current)) {
            throw new ServiceResultCodeException(UserResultCode.NOT_HAVE_PERMISSION_FOR_USER);
        }

        LocalDateTime lessonDateTime = request.getLessonDateTime();
        User memberUser = getUser(memberId);
        User employeeUser = getUser(employeeId);

        Lesson lesson = Lesson.builder()
                .employeeId(employeeUser.getId())
                .employeeName(employeeUser.getName())
                .memberId(memberUser.getId())
                .memberName(memberUser.getName())
                .lessonDateTime(lessonDateTime)
                .price(BigDecimal.ONE)
                .build();

        lessonRepository.save(lesson);
    }

    // 레슨 ID를 이용해 레슨의 상세 정보를 조회할 수 있다.
    public LessonInfo findLessonInfoById(PrincipalDetails userDetails, Long lessonId) {
        Lesson lesson = getLesson(lessonId);
        Long lessonEmployeeId = lesson.getEmployeeId();
        if(!lessonEmployeeId.equals(userDetails.getId())) {
            throw new ServiceResultCodeException(LessonResultCode.NOT_HAVE_PERSIST_FOR_LESSON);
        }

        return lessonInfoMapper.convertLessonToLessonInfo(lesson);
    }

    // 요청 날짜를 기준으로 이후 레슨정보들에 대하여 조회한다. (해당 날짜 포함)
    public LessonSearchResult findLessonAfterDate(PrincipalDetails userDetails, String date) {
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        LocalDateTime dateTime = LocalDate.of(year, month, day).atStartOfDay();

        long id = userDetails.getId();
        List<Lesson> lessons = lessonRepository
                .findLimit20ByEmployeeIdAndLessonDateTimeAfterOrderByLessonDateTime(id, dateTime);

        List<LessonInfos> lessonInfos = lessonInfoMapper.convertLessonListToLessonInfoMapByDate(lessons);
        return LessonSearchResult.builder().lessonInfosList(lessonInfos).build();
    }

    // 요청 날짜의 달을 기준으로 이전달 ~ 다음달 동안의 레슨이 존재하는 날짜를 조회한다.
    // ex) focusDate = 2023.12.02
    //     조회 기간 = 2023.11.01 ~ 2024.01.31
    public LessonDateResult findLessonDateList(PrincipalDetails userDetails, String focusDate) {
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

        return LessonDateResult.builder().lessonDates(lessonDates).build();
    }

    // 레슨 ID로 레슨 취소(삭제)가 가능하다
    public void deleteLesson(PrincipalDetails userDetails, Long lessonId) {
        Lesson lesson = getLesson(lessonId);
        Long lessonEmployeeId = lesson.getEmployeeId();
        if(!lessonEmployeeId.equals(userDetails.getId())) {
            throw new ServiceResultCodeException(LessonResultCode.NOT_HAVE_PERSIST_FOR_LESSON);
        }

        lessonRepository.deleteById(lessonId);
    }

    // 레슨ID로 레슨을 조회한다.
    private Lesson getLesson(Long id) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if(optionalLesson.isEmpty()) {
            throw new ServiceResultCodeException(LessonResultCode.NOT_EXIST_LESSON);
        }

        return optionalLesson.get();
    }

    private User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER);
        }

        return userOptional.get();
    }
}
