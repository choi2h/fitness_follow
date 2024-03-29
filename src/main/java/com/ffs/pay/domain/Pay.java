package com.ffs.pay.domain;

import com.ffs.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "PAY")
public class Pay {

    @Id
    @Column(name = "PAY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private User user;

    @Column(name = "pay")
    private BigDecimal pay;

    @Column(name = "PAY_DATE")
    private LocalDate payDate; // YYYY-MM
}
