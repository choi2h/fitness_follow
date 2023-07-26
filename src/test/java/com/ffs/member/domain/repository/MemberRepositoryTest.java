package com.ffs.member.domain.repository;

import com.ffs.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("신규 회원을 등록할 수 있다.")
    void saveMemberTest() {
        Member member = getMember();
        Member result = memberRepository.save(member);

        assertEquals(member.getName(), result.getName());
    }

    private Member getMember() {
        return  Member
                .builder()
                .name("최이화")
                .address("서울시 송파구")
                .phoneNumber("010-0000-0000")
                .loginId("qwe")
                .password("123")
                .status("일반회원")
                .build();
    }

}