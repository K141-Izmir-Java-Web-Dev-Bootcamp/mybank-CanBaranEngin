package org.kodluyoruz.mybank.service;

import lombok.extern.slf4j.Slf4j;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.dto.DebitCardDto;
import org.kodluyoruz.mybank.repository.DebitCardRepository;
import org.kodluyoruz.mybank.service.impl.DebitCardServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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



    @Override
    public DebitCard create(DebitCardDto debitCardDto) {
        DebitCard createdDebitCard = modelMapper.map(debitCardDto, DebitCard.class);
        createdDebitCard.setCustomer(customerService.getCustomerById(debitCardDto.getCustomerId()));
        createdDebitCard.setDepositAccount(depositAccountService.getDepositAccountById(debitCardDto.getAccountId()));
        createdDebitCard.setCustomerFirstName(customerService.getCustomerById(debitCardDto.getCustomerId()).getFirstName());
        createdDebitCard.setCustomerLastName(customerService.getCustomerById(debitCardDto.getCustomerId()).getLastName());
        return debitCardRepository.save(createdDebitCard);
    }

    @Override
    public List<DebitCard> getAll() {
        List<DebitCard> debitCard = (List<DebitCard>) debitCardRepository.findAll();

        return debitCard;

    }
    @Override
    public DebitCard getDebitCardById(Long id){
        Optional<DebitCard> debitCard = debitCardRepository.findById(id);
        return debitCard.orElseThrow(()->new EntityNotFoundException("DebitCard"));
    }
    @Override
    public void deleteDebitCardById(Long id) {
        Optional<DebitCard> debitCard = debitCardRepository.findById(id);
        if(debitCard.isPresent()){
            debitCardRepository.deleteById(id);
        }
        else{
            log.error("The depositaccount to be deleted does not exist");
            throw new EntityNotFoundException("DepositAccount");
        }

    }
}
