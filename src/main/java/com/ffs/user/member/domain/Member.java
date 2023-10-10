package com.ffs.user.member.domain;

import com.ffs.branch.domain.Branch;
import com.ffs.user.Role;
import com.ffs.user.User;
import com.ffs.user.employee.domain.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member extends User {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @Column(name = "STATUS")
    private String status; //(일반회원, PT회원, 휴면회원, 만기회원)


    @Builder
    public Member(Branch branch, String name, String status, String address,
                  String phoneNumber, String loginId, String password, String passwordType, String passwordSalt) {
//        this.branch = branch;
        this.status = status;

        setUserInfo(name, loginId, password, passwordType, passwordSalt, branch, Role.MEMBER, address, phoneNumber);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
