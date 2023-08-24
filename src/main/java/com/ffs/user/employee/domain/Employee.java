package com.ffs.user.employee.domain;

import com.ffs.branch.domain.Branch;
import com.ffs.user.Role;
import com.ffs.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee extends User {

    @Id
    @Column(name = "EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "STATUS")
    private String status;

    @Builder
    public Employee(Branch branch, String name, Role role, String address, String phoneNumber, String status,
                    String loginId, String password, String passwordType, String passwordSalt) {
        this.branch = branch;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;

        setUserInfo(name, loginId, password, passwordType, passwordSalt, role);
    }

    public void changeStatus(EmployeeStatus status) {
        this.status = status.getName();
    }

    public void update(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void changeBranch(Branch branch) {
        this.branch = branch;
    }
}
