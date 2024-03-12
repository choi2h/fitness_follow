package com.ffs.user.service;

import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.user.application.FindUserService;
import com.ffs.user.application.UserInfoMapper;
import com.ffs.user.domain.User;
import com.ffs.user.domain.UserType;
import com.ffs.user.domain.repository.UserRepository;
import com.ffs.user.dto.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

public class FindUserServiceTest {

    @InjectMocks
    FindUserService userService;

    @Mock
    BranchRepository branchRepository;

    @Mock
    UserRepository userRepository;

    @Spy
    UserInfoMapper userInfoMapper;

    @Test
    @DisplayName("전체 회원을 조회할 수 있다.")
    void findAllMemberTest() {
        // given
        List<User> userList = getUserList(2, 3);
        doReturn(userList).when(userRepository).findAll();

        // when
        List<UserInfo> resultList = userService.findAllUsers();

        // then
        assertEquals(userList.size(), resultList.size());
    }

    @Test
    @DisplayName("특정 지점에 있는 회원을 조회할 수 있다.")
    void findMemberUserListByBranchIdTest() {
        int memberCount = 3;
        int employeeCount = 2;

        // given
        List<User> memberList = getUserList(memberCount, employeeCount);
        doReturn(memberList).when(userRepository).findAllByBranchIdAndUserType(1L, UserType.MEMBER);

        // when
        List<UserInfo> resultList = userService.findMemberUsersByBranchId(1L);

        // then
        assertEquals(memberCount, resultList.size());
    }

    @Test
    @DisplayName("특정 지점에 있는 직원을 조회할 수 있다.")
    void findEmployeeUserListByBranchIdTest() {
        int memberCount = 3;
        int employeeCount = 2;

        // given
        List<User> memberList = getUserList(memberCount, employeeCount);
        doReturn(memberList).when(userRepository).findAllByBranchIdAndUserType(1L, UserType.EMPLOYEE);

        // when
        List<UserInfo> resultList = userService.findMemberUsersByBranchId(1L);

        // then
        assertEquals(employeeCount, resultList.size());
    }

    private List<User> getUserList(int memberCount, int employeeCount) {
        List<User> userList = new ArrayList<>();

        for(int i=1; i<=memberCount; i++) {
            User user = User.builder()
                    .name("member"+i)
                    .userType(UserType.MEMBER)
                    .build();
            userList.add(user);
        }

        for(int i=1; i<=employeeCount; i++) {
            User user = User.builder()
                    .name("employee"+i)
                    .userType(UserType.EMPLOYEE)
                    .build();
            userList.add(user);
        }

        return userList;
    }
}
