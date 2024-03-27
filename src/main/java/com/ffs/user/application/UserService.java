package com.ffs.user.application;

import com.ffs.branch.BranchResultCode;
import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.domain.Role;
import com.ffs.user.domain.User;
import com.ffs.user.UserResultCode;
import com.ffs.user.domain.UserStatus;
import com.ffs.user.domain.UserType;
import com.ffs.user.domain.repository.UserRepository;
import com.ffs.user.dto.JoinRequest;
import com.ffs.user.dto.UpdateUserRequest;
import com.ffs.user.dto.UpdateUserStatusRequest;
import com.ffs.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;


    public UserInfo join(JoinRequest request) {
        log.debug("Register new user. name={}", request.getName());

        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.info("Not exist branch for register user. id={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        User user = makeNewUser(request, branch);
        user = userRepository.save(user);

        log.info("Success to register user. userId={}, name={}", user.getId(), user.getName());
        return userInfoMapper.convertUserToUserInfo(user);
    }

    public UserInfo updateUserStatus(Long userId, UpdateUserStatusRequest request) {
        log.debug("Update user status start. userId={}, status={}", userId, request.getStatus());

        String status = request.getStatus();
        UserStatus newStatus = UserStatus.getUserStatusByName(status);
        if(newStatus == null) {
            log.info("Invalid status value. userId={}, status={}", userId, status);
            throw new ServiceResultCodeException(UserResultCode.INVALID_VALUE);
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            log.info("Not exist user by id. userId={}", userId);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER);
        }

        User user = userOptional.get();
        user.changeStatus(newStatus);
        user = userRepository.save(user);

        log.info("Success to update user status. userId={}, status={}", userId, status);
        return userInfoMapper.convertUserToUserInfo(user);
    }

    public UserInfo updateUser(Long userId, UpdateUserRequest request) {
        log.debug("Update user info. userId={}", userId);

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            log.info("Not exist user by id. userId={}", userId);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER);
        }

        String address = request.getAddress();
        String phoneNumber = request.getPhoneNumber();

        User user = userOptional.get();
        user.update(address, phoneNumber);

        log.info("Update user info. userId={}", userId);
        return userInfoMapper.convertUserToUserInfo(user);
    }

    private User makeNewUser(JoinRequest request, Branch branch) {
        String encodePassword = passwordEncoder.encode(request.getPassword());

        return User
                .builder()
                .branch(branch)
                .name(request.getName())
                .status(UserStatus.getUserStatusByName(request.getStatus()))
                .userType(UserType.getUserTypeByName(request.getUserType()))
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .loginId(request.getLoginId())
                .password(encodePassword)
                .build();
    }

    private Role getRoleByRoleName(String roleName) {
        try {
            return Role.getRoleByName(roleName);
        } catch (IllegalStateException e) {
            throw new ServiceResultCodeException(UserResultCode.INVALID_ROLE);
        }
    }
}
