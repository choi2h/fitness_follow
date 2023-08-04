package com.ffs.member.application;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.branch.BranchResultCode;
import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.member.domain.Member;
import com.ffs.member.MemberResultCode;
import com.ffs.member.domain.repository.MemberRepository;
import com.ffs.member.dto.RegisterMemberRequest;
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

    public Member registerNewMember(RegisterMemberRequest request) {
        log.debug("Register new member. name={}", request.getName());

        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch for register member. id={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        Member member = makeNewMember(request, branch);
        member = memberRepository.save(member);

        log.debug("Success to register member. memberId={}, name={}", member.getId(), member.getName());
        return member;
    }

    public List<Member> getAllMember() {
        List<Member> memberList = memberRepository.findAll();
        if(memberList.isEmpty()) {
            log.debug("Not exist member anyone.");
            throw new ServiceResultCodeException(MemberResultCode.NO_REGISTERED_MEMBER);
        }

        return memberList;
    }

    public Member getMemberById(Long id) {
        log.debug("Search member by id. id={}", id);

        Optional<Member> optionalMember = memberRepository.findById(id);
        if(optionalMember.isEmpty()) {
            log.debug("Not exist member from id. id={}", id);
            throw new ServiceResultCodeException(MemberResultCode.NOT_EXIST_MEMBER, id);
        }

        Member member = optionalMember.get();
        log.debug("Found member by id. id={}, name={}", id, member.getName());

        return member;
    }

    public List<Member> getMemberListByBranchId(Long branchId) {
        log.debug("Search member list by branch id. branchId={}", branchId);

        List<Member> memberList = memberRepository.findAllByBranchId(branchId);
        if(memberList.isEmpty()) {
            log.debug("Not exist member by branch id. branchId={}", branchId);
            throw new ServiceResultCodeException(MemberResultCode.NOT_EXIST_MEMBER_FOR_BRANCH, branchId);
        }

        log.debug("Found member list by branch id. count={}", memberList.size());
        return memberList;

    }

    //TODO 특정 트레이너가 담당하는 회원 리스트를 조회할 수 있다.

    //TODO 자신의 개인정보를 조회할 수 있다. (멤버십 / PT / 락카)

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