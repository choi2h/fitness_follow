package com.ffs.user.application;

import com.ffs.user.domain.User;
import com.ffs.user.dto.UserInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserInfoMapper {

    protected UserInfo convertUserToUserInfo(User user) {
        return UserInfo
                .builder()
                .id(user.getId())
                .branchId(Objects.isNull(user.getBranch()) ? null :  user.getBranch().getId())
                .branchName(Objects.isNull(user.getBranch()) ? null :  user.getBranch().getName())
                .name(user.getName())
                .status(user.getStatus().name())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .loginId(user.getLoginId())
                .build();
    }

    protected List<UserInfo> convertUserListToUserInfoList(List<User> memberList) {
        List<UserInfo> userInfoList = new ArrayList<>();

        for(User member : memberList) {
            UserInfo userInfo = this.convertUserToUserInfo(member);
            userInfoList.add(userInfo);
        }

        return userInfoList;
    }
}
