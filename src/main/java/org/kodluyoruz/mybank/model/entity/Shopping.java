package org.kodluyoruz.mybank.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Shopping")
@Data
public class Shopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "spending")
    private Long spending;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
