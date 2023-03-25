package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "BRANCH")
public class Branch {

    @Id
    @Column(name = "BRANCH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private BranchGroup branchGroup;

    @OneToMany(mappedBy = "branch")
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "branch")
    private List<Member> memberList;

    @OneToMany(mappedBy = "branch")
    private List<Product> productList;

    public void setBranchGroup(BranchGroup branchGroup) {
        this.branchGroup = branchGroup;

        if(!branchGroup.getBranchList().equals(this)) {
            branchGroup.addBranch(this);
        }
    }

    public void addEmployee(Employee employee) {
        this.employeeList.add(employee);

        if(!employee.getBranch().equals(this)) {
            employee.setBranch(this);
        }
    }

    public void addMember(Member member) {
        this.memberList.add(member);

        if(!member.getBranch().equals(this)) {
            member.setBranch(this);
        }
    }

    public void addProduct(Product product) {
        this.productList.add(product);

        if(!product.getBranch().equals(this)) {
            product.setBranch(this);
        }
    }
}
