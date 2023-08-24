package com.ffs.auth;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.User;
import com.ffs.user.UserResultCode;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.employee.domain.repository.EmployeeRepository;
import com.ffs.user.member.domain.Member;
import com.ffs.user.member.domain.repository.MemberRepository;
import com.ffs.user.member.dto.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserProvider {

    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;

    @Getter
    @AllArgsConstructor
    private enum LoginType {
        ADMIN, MEMBER;
    }

    public User getUser(LoginRequest loginRequest) {
        String type = loginRequest.getType();

        if(type.equals(LoginType.ADMIN.name())) {
            return getAdmin(loginRequest.getId());
        } else if (type.equals(LoginType.MEMBER.name())) {
            return getMember(loginRequest.getId());
        } else {
            throw new IllegalStateException();
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
