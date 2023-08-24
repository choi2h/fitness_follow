package com.ffs.auth;

import com.ffs.user.User;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.employee.domain.repository.EmployeeRepository;
import com.ffs.user.member.domain.Member;
import com.ffs.user.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {


    private final EmployeeRepository employeeRepository;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        Optional<Employee> employeeOptional = employeeRepository.findByLoginId(username);

        User user = null;
        if(employeeOptional.isEmpty()) {
            Optional<Member> memberOptional = memberRepository.findByLoginId(username);
            user = memberOptional.get();
        } else {
            user = employeeOptional.get();
        }

        System.out.println("PrincipalDetailsService의 user : " + user);
        return new PrincipalDetails(user);
    }
}
