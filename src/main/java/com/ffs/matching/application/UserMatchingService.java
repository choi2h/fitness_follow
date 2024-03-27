package com.ffs.matching.application;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.matching.domain.UserMatching;
import com.ffs.matching.domain.repository.UserMatchingRepository;
import com.ffs.matching.dto.CheckMatchingResponse;
import com.ffs.matching.dto.MatchingRequest;
import com.ffs.user.UserResultCode;
import com.ffs.user.domain.User;
import com.ffs.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMatchingService {

    private final UserRepository userRepository;
    private final UserMatchingRepository userMatchingRepository;

    // 직원-회원 매칭(담당자 지정)
    public void matchingMemberAndEmployee(MatchingRequest matchingRequest) {
        Long memberId = matchingRequest.getMemberUserId();
        Long employeeId = matchingRequest.getEmployeeUserId();

        List<UserMatching> userMatchingList = userMatchingRepository.findAllByMemberIdOrderByFinishedAt(memberId);
        if(!userMatchingList.isEmpty()) {
            LocalDateTime lastFinishedAt = userMatchingList.get(userMatchingList.size()-1).getFinishedAt();
            if(lastFinishedAt != null && lastFinishedAt.isAfter(LocalDateTime.now())) {
                throw new ServiceResultCodeException(UserResultCode.INVALID_VALUE);
            }
        }

        UserMatching userMatching = UserMatching.builder()
                .memberId(memberId)
                .employeeId(employeeId)
                .createdAt(LocalDateTime.now())
                .build();
        userMatchingRepository.save(userMatching);
    }

    // 서로 매칭되어 있는 회원인지 확인
    public CheckMatchingResponse checkMatchingMember(Long memberId, Long employeeId) {
        Optional<UserMatching> userMatchingOptional = userMatchingRepository.findByMemberIdAndEmployeeId(memberId, employeeId);

        User memberUser = findUser(memberId);
        User employeeUser = findUser(employeeId);

        if(userMatchingOptional.isEmpty()) {
            return new CheckMatchingResponse(memberId, memberUser.getName(), employeeId, employeeUser.getName(), false);
        }

        return new CheckMatchingResponse(memberId,  memberUser.getName(), employeeId, employeeUser.getName(), true);
    }

    private User findUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER);
        }

        return userOptional.get();
    }
}
