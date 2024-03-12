package com.ffs.user.application;

import com.ffs.user.domain.User;
import com.ffs.user.dto.UserInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserInfoMapper {

    protected UserInfo convertUserToUserInfo(User member) {
        return UserInfo
                .builder()
                .id(member.getId())
                .branchId(Objects.isNull(member.getBranch()) ? null :  member.getBranch().getId())
                .branchName(Objects.isNull(member.getBranch()) ? null :  member.getBranch().getName())
                .name(member.getName())
                .status(member.getStatus())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .loginId(member.getLoginId())
                .build();
    }

    protected List<UserInfo> converUserListToUserInfoList(List<User> memberList) {
        List<UserInfo> userInfoList = new ArrayList<>();

        for(User member : memberList) {
            UserInfo userInfo = this.convertUserToUserInfo(member);
            userInfoList.add(userInfo);
        }

        return userInfoList;
    }
}
