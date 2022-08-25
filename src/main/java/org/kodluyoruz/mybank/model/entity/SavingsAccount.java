package org.kodluyoruz.mybank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavingsAccount extends Account {

    @Column(name = "Savings_Account_Type")
    private String savingsAccountType;
    @Column(name="Bank_Rate")
    private Double bankRate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
