package com.ffs.auth;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.UserResultCode;
import com.ffs.user.domain.User;
import com.ffs.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserProvider {

    private final UserRepository userRepository;

    public AuthUser getAuthUser(String loginId) {
        Optional<User> userOptional = userRepository.findByLoginId(loginId);
        if(userOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER);
        }

        User user = userOptional.get();
        return getAuthUser(user.getId(), user);
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
