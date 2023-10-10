package com.ffs.auth;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.Role;
import com.ffs.user.User;
import com.ffs.user.UserResultCode;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.employee.domain.repository.EmployeeRepository;
import com.ffs.user.member.domain.Member;
import com.ffs.user.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserProvider {

    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;

    public AuthUser getAuthUser(String loginId, String role) {
        if (role.equals(Role.MEMBER.getRoleText())) {
            Member member = getMember(loginId);
            return getAuthUser(member.getId(), member);
        } else {
            Employee employee = getAdmin(loginId);
            return getAuthUser(employee.getId(), employee);
        }
    }

    public AuthUser getAuthUser(String loginId) {
        Optional<Employee> employeeOptional = employeeRepository.findByLoginId(loginId);
        if(employeeOptional.isEmpty()) {
            Optional<Member> memberOptional =memberRepository.findByLoginId(loginId);
            if(memberOptional.isEmpty()) {
                return null;
            } else {
                Member member = memberOptional.get();
                return getAuthUser(member.getId(), member);
            }
        }

        Employee employee = employeeOptional.get();
        return getAuthUser(employee.getId(), employee);
    }

    private Employee getAdmin(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findByLoginId(id);

        if(employeeOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_EMPLOYEE);
        }

        return employeeOptional.get();
    }

    private Member getMember(String id) {
        Optional<Member> memberOptional =memberRepository.findByLoginId(id);

        if(memberOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_MEMBER);
        }

        return memberOptional.get();
    }

    private AuthUser getAuthUser(Long id, User user) {
        return AuthUser.builder()
                .id(id)
                .name(user.getName())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .role(user.getRole())
                .branchId(user.getBranch().getId())
                .build();

    }
}
