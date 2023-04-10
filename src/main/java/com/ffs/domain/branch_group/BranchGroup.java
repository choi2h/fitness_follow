package com.ffs.domain.branch_group;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BRANCH_GROUP")
public class BranchGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Builder
    public BranchGroup(String name) {
        this.name = name;
    }

}
