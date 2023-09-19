package com.ffs.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AuthToken {

    @Id
    private String memberId;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    public AuthToken(String memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }
}
