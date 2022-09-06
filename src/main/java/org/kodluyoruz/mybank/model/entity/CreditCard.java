package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "CreditCards")
@Data
public class CreditCard extends Card{
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "depositAccount_id")
    private DepositAccount depositAccount;
    @Column(name = "Credit_Card_Limit",nullable = false)
    private Double creditCardLimit=500.0;
    private Double creditCardDebtValue=0.0;



}
