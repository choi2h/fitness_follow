package com.ffs.user.member.application;

import com.ffs.user.member.domain.Member;
import com.ffs.user.member.dto.MemberInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MemberInfoMapper {

    protected MemberInfo convertMemberToMemberInfo(Member member) {
        return MemberInfo
                .builder()
                .id(member.getId())
                .branchId(Objects.isNull(member.getBranch()) ? null :  member.getBranch().getId())
                .branchName(Objects.isNull(member.getBranch()) ? null :  member.getBranch().getName())
                .employeeId(Objects.isNull(member.getEmployee()) ? null :  member.getEmployee().getId())
                .employeeName(Objects.isNull(member.getEmployee()) ? null :  member.getEmployee().getName())
                .name(member.getName())
                .status(member.getStatus())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .loginId(member.getLoginId())
                .build();
    }

    protected List<MemberInfo> convertMemberListToMemberInfoList(List<Member> memberList) {
        List<MemberInfo> memberInfoList = new ArrayList<>();

        for(Member member : memberList) {
            MemberInfo memberInfo = this.convertMemberToMemberInfo(member);
            memberInfoList.add(memberInfo);
        }

        return memberInfoList;
    }
}
