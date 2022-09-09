package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "DebitCards")
@Data
public class DebitCard extends Card{
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "depositAccount_id")
    private DepositAccount depositAccount;
    private CardType cardType=CardType.DEBITCARD;


}
