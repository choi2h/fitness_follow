package com.ffs.user.common;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.UserResultCode;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.employee.domain.repository.EmployeeRepository;
import com.ffs.user.member.domain.Member;
import com.ffs.user.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommonService {

    private final EmployeeRepository employeeRepository;
    private final MemberRepository memberRepository;


    // 직원에게 담당 회원 지정
    public void matchingMemberAndEmployee(MatchingRequest matchingRequest) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(matchingRequest.getEmployeeId());
        if(optionalEmployee.isEmpty()) {
            log.info("Not exist employee by id. employeeId={}", matchingRequest.getEmployeeId());
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_EMPLOYEE);
        }

        Member member = getMemberByMemberId(matchingRequest.getMemberId());
        Employee employee = optionalEmployee.get();
        member.setEmployee(employee);

        memberRepository.save(member);
    }

    // 직원이 담당하는 회원인지 확인
    public void checkMatchingMember(Long memberId, Long employeeId) {
        Member member = getMemberByMemberId(memberId);

        Employee employee = member.getEmployee();
        if(employee == null && !employee.getId().equals(employeeId)) {
            throw new ServiceResultCodeException(UserResultCode.NOT_HAVE_PERMISSION_FOR_MEMBER, memberId, employeeId);
        }
    }

    private Member getMemberByMemberId(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()) {
            log.info("Not exist member from id. id={}", memberId);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_MEMBER, memberId);
        }

        return optionalMember.get();
    }
}
