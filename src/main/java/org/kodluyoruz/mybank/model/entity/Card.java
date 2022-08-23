package org.kodluyoruz.mybank.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class Card implements Serializable {

    @Column(name = "Customer_Firstname")
    private String customerFirstName;
    @Column(name="Customer_Lastname")
    private String customerLastName;
    @Column(name="CVC")
    private int cvc;
}
