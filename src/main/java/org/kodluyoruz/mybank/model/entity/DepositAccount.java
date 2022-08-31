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
public class DepositAccount extends Account {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(mappedBy = "depositAccount")
    private DebitCard debitCard;
    @OneToOne(mappedBy = "depositAccount")
    private CreditCard creditCard;
    private final String accountType="deposit";

}
