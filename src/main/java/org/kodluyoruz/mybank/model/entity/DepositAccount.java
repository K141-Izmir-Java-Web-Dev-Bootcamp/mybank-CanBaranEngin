package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class DepositAccount extends Account {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(mappedBy = "depositAccount")
    private DebitCard debitCard;
    @OneToOne(mappedBy = "depositAccount")
    private CreditCard creditCard;
}
