package com.ffs.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.dao.Branch;
import com.ffs.dao.Member;
import com.ffs.dto.RegisterMemberRequest;
import com.ffs.repository.BranchRepository;
import com.ffs.repository.MemberRepository;
import com.ffs.util.BranchResultCode;
import com.ffs.util.MemberResultCode;
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

    public Long registerNewEmployee(RegisterMemberRequest request) {
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

        return member.getId();
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

    private Member makeNewMember(RegisterMemberRequest request, Branch branch) {
        Member member = new Member();
        member.setBranch(branch);
        member.setName(request.getName());
        member.setStatus(request.getStatus());
        member.setAddress(request.getAddress());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setLoginId(request.getLoginId());
        member.setPassword(request.getPassword());
        return member;
    }
}
