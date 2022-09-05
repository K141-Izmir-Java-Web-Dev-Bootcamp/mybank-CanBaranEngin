package org.kodluyoruz.mybank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "Email",length = 100)
    private String email;
    @Column(name = "Birthday",length = 20)
    private LocalDate birthday;
    @JsonIgnore
    @OneToMany
    private List<DepositAccount> depositAccount;
    @JsonIgnore
    @OneToMany
    private List<SavingsAccount> savingsAccount;
    @JsonIgnore
    @OneToMany
    private List<DebitCard> debitCardList ;
    @JsonIgnore
    @OneToMany
    private List<CreditCard> creditCardList ;
    @JsonIgnore
    @OneToMany
    private List<Shopping> shoppingList;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Customers_Transfer ",
            joinColumns = @JoinColumn(name = "Customers_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Transfer_id",referencedColumnName = "id"))
    private Set<Transfer> transferList;




}
