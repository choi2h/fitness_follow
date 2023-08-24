package com.ffs.user.member.application;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.branch.BranchResultCode;
import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.user.member.domain.Member;
import com.ffs.user.UserResultCode;
import com.ffs.user.member.domain.repository.MemberRepository;
import com.ffs.user.member.dto.MemberInfo;
import com.ffs.user.member.dto.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final BranchRepository branchRepository;
    private final MemberRepository memberRepository;
    private final MemberInfoMapper memberInfoMapper;

    public MemberInfo registerNewMember(RegisterMemberRequest request) {
        log.debug("Register new member. name={}", request.getName());

        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.info("Not exist branch for register member. id={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        Member member = makeNewMember(request, branch);
        member = memberRepository.save(member);

        log.info("Success to register member. memberId={}, name={}", member.getId(), member.getName());
        return memberInfoMapper.convertMemberToMemberInfo(member);
    }

    public List<MemberInfo> getAllMembers() {
        List<Member> memberList = memberRepository.findAll();
        if(memberList.isEmpty()) {
            log.info("Not exist member anyone.");
            throw new ServiceResultCodeException(UserResultCode.NO_REGISTERED_MEMBER);
        }

        log.info("Found all members. count={}", memberList.size());
        return memberInfoMapper.convertMemberListToMemberInfoList(memberList);
    }

    public MemberInfo getMemberById(Long id) {
        log.debug("Search member by id. id={}", id);

        Optional<Member> optionalMember = memberRepository.findById(id);
        if(optionalMember.isEmpty()) {
            log.info("Not exist member from id. id={}", id);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_MEMBER, id);
        }

        Member member = optionalMember.get();
        log.info("Found member by id. id={}, name={}", id, member.getName());

        return memberInfoMapper.convertMemberToMemberInfo(member);
    }

    public List<MemberInfo> getMembersByBranchId(Long branchId) {
        log.debug("Search members by branch id. branchId={}", branchId);

        List<Member> memberList = memberRepository.findAllByBranchId(branchId);
        if(memberList.isEmpty()) {
            log.info("Not exist member by branch id. branchId={}", branchId);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_MEMBER_FOR_BRANCH, branchId);
        }

        log.info("Found member list by branch id. count={}", memberList.size());
        return memberInfoMapper.convertMemberListToMemberInfoList(memberList);
    }

    public List<MemberInfo> getMembersByEmployeeId(Long employeeId) {
        log.debug("Search members by employee id. employeeId={}", employeeId);

        List<Member> memberList = memberRepository.findAllByEmployeeId(employeeId);
        if(memberList.isEmpty()) {
            log.info("Not exist member by employee id. employeeId={}", employeeId);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_MEMBER_FOR_BRANCH, employeeId);
        }

        log.info("Found member list by employee id. count={}", memberList.size());
        return memberInfoMapper.convertMemberListToMemberInfoList(memberList);
    }

    //TODO 자신의 개인정보를 조회할 수 있다. (멤버십 / PT / 락카) -> 다른 서비스에서 구현

    //TODO 만기된 회원의 정보는 2년 뒤에 자동 삭제된다.


    private Member makeNewMember(RegisterMemberRequest request, Branch branch) {
       return Member
                .builder()
                .branch(branch)
                .name(request.getName())
                .status(request.getStatus())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .build();
    }
}
