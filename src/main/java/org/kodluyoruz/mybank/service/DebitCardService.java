package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.dto.DebitCardDto;
import org.kodluyoruz.mybank.repository.DebitCardRepository;
import org.kodluyoruz.mybank.service.impl.DebitCardServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DebitCardService implements DebitCardServiceImpl {

    private final DebitCardRepository debitCardRepository;
    private final CustomerService customerService;
    private final DepositAccountService depositAccountService;


    private final ModelMapper modelMapper;

    public DebitCardService(DebitCardRepository debitCardRepository, CustomerService customerService, DepositAccountService depositAccountService, ModelMapper modelMapper) {
        this.debitCardRepository = debitCardRepository;
        this.customerService = customerService;
        this.depositAccountService = depositAccountService;
        this.modelMapper = modelMapper;
    }




    public DebitCard create(DebitCardDto debitCardDto) {
        DebitCard createdDebitCard = modelMapper.map(debitCardDto, DebitCard.class);
        createdDebitCard.setCustomer(customerService.getCustomerById(debitCardDto.getCustomerId()));
        createdDebitCard.setDepositAccount(depositAccountService.getDepositAccountById(debitCardDto.getAccountId()));
        return debitCardRepository.save(createdDebitCard);

    }
}
