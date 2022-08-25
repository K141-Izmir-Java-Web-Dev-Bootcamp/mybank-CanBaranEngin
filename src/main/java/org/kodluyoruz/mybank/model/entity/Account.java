package org.kodluyoruz.mybank.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Account_Name")
    private String name;
    @Column(name = "IBAN",unique = true)
    private Long iban;
    @Column(name = "Created_Date")
    private LocalDate createdDate;
    @Column(name = "Account_Balance")
    private Double accountBalance;
    @Column(name = "Money_Type",length = 10)
    private String moneyType;


}
