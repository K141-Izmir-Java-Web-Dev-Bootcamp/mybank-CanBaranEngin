package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class CreditCard extends Card{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "depositAccount_id")
    private DepositAccount depositAccount;
    @Column(name = "Credit_Card_Limit")
    private Double creditCardLimit;


}
