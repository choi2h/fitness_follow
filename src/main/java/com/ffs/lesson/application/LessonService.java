package com.ffs.lesson.application;

import com.ffs.auth.AuthUser;
import com.ffs.auth.PrincipalDetails;
import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.lesson.domain.Lesson;
import com.ffs.lesson.domain.repository.LessonRepository;
import com.ffs.lesson.dto.FindLessonRequest;
import com.ffs.lesson.dto.FindLessonResult;
import com.ffs.lesson.dto.LessonInfo;
import com.ffs.lesson.dto.RegisterLessonRequest;
import com.ffs.user.Role;
import com.ffs.user.UserResultCode;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.employee.domain.repository.EmployeeRepository;
import com.ffs.user.member.domain.Member;
import com.ffs.user.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService {

    private final EmployeeRepository employeeRepository;
    private final MemberRepository memberRepository;
    private final LessonRepository lessonRepository;
    private final LessonInfoMapper lessonInfoMapper;

    //TODO 레슨의 상태는 예약/취소/완료/결석 네가지로 관리될 수 있다.
    public void updateLessonState() {


    }

    //TODO 조건에 맞춰 자신의 레슨을 조회할 수 있다.
    // 날짜(월별, 일별, 설정 기간 내) / 상태 / 회원
    public FindLessonResult findLessonForDate(PrincipalDetails userDetails, FindLessonRequest findLessonRequest) {
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
            lessonList = lessonRepository.findAllByEmployeeIdAndLessonDateTimeBetween(userId, startTime, endTime);
        }

        log.debug("Get lessons. role={}, id={}, lessonLise={}", role, userId, lessonList.size());

        List<LessonInfo> lessonInfoList = lessonInfoMapper.convertLessonListToLessonInfoList(lessonList);
        return FindLessonResult.builder().lessonInfoList(lessonInfoList).build();
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
    protected void getLessonInfo() {


    }

    //TODO 레슨 취소(삭제)가 가능하다
    protected void deleteLesson() {


    }
}
