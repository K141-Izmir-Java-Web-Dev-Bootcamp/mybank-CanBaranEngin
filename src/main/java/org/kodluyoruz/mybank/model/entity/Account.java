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
    @SequenceGenerator(name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_sequence")
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
    @Column(name = "CustomerIdentity_Number",length = 20)
    private Long customerIdentityNumber;

}
