package com.ffs.user.service;

import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.user.application.UserInfoMapper;
import com.ffs.user.application.UserService;
import com.ffs.user.domain.User;
import com.ffs.user.domain.repository.UserRepository;
import com.ffs.user.dto.JoinRequest;
import com.ffs.user.dto.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    BranchRepository branchRepository;

    @Mock
    UserRepository memberRepository;

    @Spy
    UserInfoMapper memberInfoMapper;

    @Test
    @DisplayName("회원을 등록할 수 있다.")
    void registerMemberTest() {
        // given
        User user = User.builder().name("최이화").build();
        doReturn(Optional.of(new Branch())).when(branchRepository).findById(1L);
        doReturn(user).when(memberRepository).save(any(User.class));
        JoinRequest request = getJoinRequest();

        // when
        UserInfo result = userService.join(request);

        // then
        assertEquals(user.getName(), result.getName());
    }

    private JoinRequest getJoinRequest() {
        return new JoinRequest
                (1L, "최이화", "일반회원", "서울시", "010-0000-0000", "qwe", "1234");
    }
}
