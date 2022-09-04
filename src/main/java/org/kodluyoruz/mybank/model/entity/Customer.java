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
    @SequenceGenerator(name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_sequence")
    private Long id;
    @Column(name = "IdentiytyNumber")
    private Long identityNumber;
    @Column(name = "First_Name",length = 50)
    private String firstName;
    @Column(name = "Last_Name",length = 50)
    private String lastName;
    @Column(name = "Password",length = 100)
    private String email;
    @Column(name = "Birthday",length = 20)
    private LocalDate birthday;
    @OneToMany
    private List<DepositAccount> depositAccount;
    @OneToMany
    private List<SavingsAccount> savingsAccount;
    @OneToMany
    private List<DebitCard> debitCardList ;
    @OneToMany
    private List<CreditCard> creditCardList ;
    @OneToMany
    private List<Shopping> shoppingList;
    @ManyToMany
    @JoinTable(
            name = "Customers_Transfer ",
            joinColumns = @JoinColumn(name = "Customers_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Transfer_id",referencedColumnName = "id"))
    private Set<Transfer> transferList;




}
