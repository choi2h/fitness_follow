package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "BRANCH_GROUP")
public class BranchGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "branchGroup")
    private List<Branch> branchList;

    public void addBranch(Branch branch) {
        this.branchList.add(branch);

        if(!branch.getBranchGroup().equals(this)) {
            branch.setBranchGroup(this);
        }
    }
}
