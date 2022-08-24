package org.kodluyoruz.mybank.model.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class CustomerDto {
    private Long identityNumber;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private LocalDate birthday;
}
