package org.kodluyoruz.mybank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card implements Serializable {
    @Id
    @SequenceGenerator(name = "card_sequence",
            sequenceName = "card_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "card_sequence")
    private Long id;
    @Column(name = "Customer_Firstname")
    private String customerFirstName;
    @Column(name="Customer_Lastname")
    private String customerLastName;
    @Column(name="CVC",length = 3)
    private String cvc;
    @Column(name="password",nullable = false,length = 4)
    private String password;
}
