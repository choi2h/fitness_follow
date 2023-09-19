package com.ffs.auth;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.Role;
import com.ffs.user.User;
import com.ffs.user.UserResultCode;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.employee.domain.repository.EmployeeRepository;
import com.ffs.user.member.domain.Member;
import com.ffs.user.member.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserProvider {

    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;

    public User getUser(String role, String loginId) {
        if (role.equals(Role.MEMBER.getRoleText())) {
            return getMember(loginId);
        } else {
            return getAdmin(loginId);
        }
    }

    private User getAdmin(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findByLoginId(id);

        if(employeeOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_EMPLOYEE);
        }

        return employeeOptional.get();
    }

    private User getMember(String id) {
        Optional<Member> memberOptional =memberRepository.findByLoginId(id);

        if(memberOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_MEMBER);
        }

        return memberOptional.get();
    }
}
