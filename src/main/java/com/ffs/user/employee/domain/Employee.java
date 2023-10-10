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

    @Column(name = "STATUS")
    private String status;

    @Builder
    public Employee(Branch branch, String name, Role role, String address, String phoneNumber, String status,
                    String loginId, String password, String passwordType, String passwordSalt) {
        this.status = status;
        setUserInfo(name, loginId, password, passwordType, passwordSalt, branch, role, address, phoneNumber);
    }

    public void changeStatus(EmployeeStatus status) {
        this.status = status.getName();
    }
}
