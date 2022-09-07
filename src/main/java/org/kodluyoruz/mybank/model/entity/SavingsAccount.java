package org.kodluyoruz.mybank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SavingsAccounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavingsAccount extends Account {

    @Column(name="Bank_Rate")
    private Double bankRate;
    private AccountType accountType=AccountType.SAVINGSACCOUNT;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
