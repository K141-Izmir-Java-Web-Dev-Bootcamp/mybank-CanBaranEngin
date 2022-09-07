package org.kodluyoruz.mybank.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private Long identityNumber;


}
