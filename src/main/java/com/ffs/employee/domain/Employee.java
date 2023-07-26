package com.ffs.employee.domain;

import com.ffs.branch.domain.Branch;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee {

    /**
     * 재직상태
     */
    @Getter
    @AllArgsConstructor
    public enum Status {
        RESIGN("퇴사"),
        EMPLOYED("재직중");

        private String name;

        public static Status getStatusByName(String name) {
            Status result = null;

            for(Status status : Employee.Status.values()) {
                if(name.equals(status.getName())){
                    result = status;
                    break;
                }
            }

            return result;
        }
    }


    /**
     * 직책
     */
    @Getter
    @RequiredArgsConstructor
    public enum Responsibility {
        CEO("대표"),
        MANAGER("매니저"),
        TRAINER("트레이너");

        private final String text;
    }

    @Id
    @Column(name = "EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RESPONSIBILITY")
    private String responsibility; //(대표, 점장, 매니저, 트레이너, FC)

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_TYPE")
    private String passwordType;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;

    @Builder
    public Employee(Branch branch, String name, String responsibility, String address, String phoneNumber, String status,
                    String loginId, String password, String passwordType, String passwordSalt) {
        this.branch = branch;
        this.name = name;
        this.responsibility = responsibility;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.loginId = loginId;
        this.password = password;
        this.passwordType = passwordType;
        this.passwordSalt = passwordSalt;
    }

    public void setStatus(Status status) {
        this.status = status.getName();
    }

}
