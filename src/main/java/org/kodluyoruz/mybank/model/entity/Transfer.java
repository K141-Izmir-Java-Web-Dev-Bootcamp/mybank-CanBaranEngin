package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Data
public class Transfer {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Sender_IBAN")
    private Long SenderIBAN;
    @Column(name = "Receiver_IBAN")
    private Long ReceiverIBAN;
    @Column(name = "Money_Value")
    private Double moneyValue;
    @Column(name = "Transfer_Date")
    private LocalDate transferDate;
    @ManyToMany(mappedBy = "transferList")
    private Set<Customer> customers;

}

