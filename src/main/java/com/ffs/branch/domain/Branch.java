package com.ffs.branch.domain;

import com.ffs.branch_group.domain.BranchGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    public Branch(String name, String address, String phoneNumber, BranchGroup branchGroup) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branchGroup = branchGroup;
    }

    public void updateBranchInfo(String address, String phoneNumber) {
        if(!address.equals(this.address)) {
            this.address = address;
        }

        if(!phoneNumber.equals(this.phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
    }

}
