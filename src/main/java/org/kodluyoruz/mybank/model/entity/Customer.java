package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Customers")
@Data
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Identity_Number",length = 20,unique = true)
    private Long identityNumber;
    @Column(name = "First_Name",length = 50)
    private String firstName;
    @Column(name = "Last_Name",length = 50)
    private String lastName;
    @Column(name = "Password",length = 100)
    private String password;
    @Column(name = "Email",length = 20)
    private String email;
    @Column(name = "Birthday",length = 20)
    private LocalDate birthday;
    @OneToMany(mappedBy = "customer")
    private List<DepositAccount> depositAccount;
    @OneToMany(mappedBy = "customer")
    private List<SavingsAccount> savingsAccount;
    @OneToMany(mappedBy = "customer")
    private List<DebitCard> debitCardList ;
    @OneToMany(mappedBy = "customer")
    private List<CreditCard> creditCardList ;
    @ManyToMany
    @JoinTable(
            name = "Customers_Transfer ",
            joinColumns = @JoinColumn(name = "Customers_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Transfer_id",referencedColumnName = "id"))
    private Set<Transfer> transferList;

}
