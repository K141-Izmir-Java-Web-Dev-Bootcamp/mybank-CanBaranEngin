package org.kodluyoruz.mybank.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DepositAccounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositAccount extends Account {
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    private DebitCard debitCard;
    @OneToOne
    private CreditCard creditCard;
    private final String accountType="deposit";

}
