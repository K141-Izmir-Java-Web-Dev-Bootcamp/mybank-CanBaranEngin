package org.kodluyoruz.mybank.model.entity.dto;

import lombok.Data;
import org.kodluyoruz.mybank.model.entity.Customer;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.Set;

@Data
public class TransferDto {

    private Long ReceiverIBAN;
    private Long SenderIBAN;
    private Double moneyValue;
}
