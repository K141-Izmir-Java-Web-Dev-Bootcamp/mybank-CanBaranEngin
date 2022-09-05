package org.kodluyoruz.mybank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Shoppings")
@Data
public class Shopping {
    @Id
    @SequenceGenerator(name = "shopping_sequence",
            sequenceName = "shopping_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "shopping_sequence")
    private Long id;
    @Column(name = "spending")
    private Long spending;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @JsonIgnore
    @Column
    private CardType cardType;
}
