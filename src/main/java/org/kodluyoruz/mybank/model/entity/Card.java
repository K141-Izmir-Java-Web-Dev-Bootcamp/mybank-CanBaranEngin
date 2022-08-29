package org.kodluyoruz.mybank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Customer_Firstname")
    private String customerFirstName;
    @Column(name="Customer_Lastname")
    private String customerLastName;
    @Column(name="CVC")
    private int cvc;
}
