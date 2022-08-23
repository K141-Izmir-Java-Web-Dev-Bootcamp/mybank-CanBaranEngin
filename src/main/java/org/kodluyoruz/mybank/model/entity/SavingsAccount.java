package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table
public class SavingsAccount extends Account {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Savings_Account_Type")
    private String savingsAccountType;
    @Column(name="Bank_Rate")
    private Double bankRate;
    @OneToOne(mappedBy = "savingsAccount")
    private Customer customer;
}
