package com.ffs.user.application;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.domain.User;
import com.ffs.user.UserResultCode;
import com.ffs.user.domain.UserType;
import com.ffs.user.domain.repository.UserRepository;
import com.ffs.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindUserService {

    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;

    // 전체 회원 정보 조회
    public List<UserInfo> findAllUsers() {
        List<User> memberList = userRepository.findAll();
        if(memberList.isEmpty()) {
            log.info("Not exist member anyone.");
            throw new ServiceResultCodeException(UserResultCode.NO_REGISTERED_USER);
        }

        log.info("Found all members. count={}", memberList.size());
        return userInfoMapper.converUserListToUserInfoList(memberList);
    }

    // 회원ID로 회원 정보 조회
    public UserInfo findUserById(Long id) {
        log.debug("Search member by id. id={}", id);

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            log.info("Not exist member from id. id={}", id);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER, id);
        }

        User user = optionalUser.get();
        log.info("Found member by id. id={}, name={}", id, user.getName());

        return userInfoMapper.convertUserToUserInfo(user);
    }

    // 지점에 등록되어 있는 회원 정보 조회
    public List<UserInfo> findMemberUsersByBranchId(Long branchId) {
        log.debug("Search member users by branch id. branchId={}", branchId);

        List<User> userList = findUserListByBranchIdAndUserType(branchId, UserType.MEMBER);

        log.info("Found member user list by branch id. count={}", userList.size());
        return userInfoMapper.converUserListToUserInfoList(userList);
    }

    // 지점에 등록되어 있는 직원 정보 조회
    public List<UserInfo> findEmployeeUsersByBranchId(Long branchId) {
        log.debug("Search employee users by branch id. branchId={}", branchId);

        List<User> userList = findUserListByBranchIdAndUserType(branchId, UserType.EMPLOYEE);

        log.info("Found member user list by branch id. count={}", userList.size());
        return userInfoMapper.converUserListToUserInfoList(userList);
    }

    private List<User> findUserListByBranchIdAndUserType(Long branchId, UserType userType) {
        List<User> userList = userRepository.findAllByBranchIdAndUserType(branchId, userType);
        if(userList.isEmpty()) {
            log.info("Not exist user by branch id. branchId={}, userType={}", branchId, userType.getName());
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER_FOR_BRANCH, branchId);
        }

        return userList;
    }

}
