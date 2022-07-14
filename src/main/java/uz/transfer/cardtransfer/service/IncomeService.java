package uz.transfer.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.transfer.cardtransfer.entity.Card;
import uz.transfer.cardtransfer.entity.Income;
import uz.transfer.cardtransfer.payload.IncomeDto;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.repository.CardRepository;
import uz.transfer.cardtransfer.repository.IncomeRepository;

import java.util.Optional;

@Service
public class IncomeService {
    final IncomeRepository incomeRepository;
    final CardRepository cardRepository;

    public IncomeService(IncomeRepository incomeRepository, CardRepository cardRepository) {
        this.incomeRepository = incomeRepository;
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<Page<Income>> getIncomes(int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Card> cardByUsernameOptional = cardRepository.findCardByUsername(String.valueOf(principal));
        return cardByUsernameOptional.map(card -> ResponseEntity.ok(incomeRepository.findAllByFromCardId(PageRequest.of(page, size), card))).orElse(null);
    }

    public ResponseEntity<Income> getIncome(Long id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        return optionalIncome.map(income -> ResponseEntity.status(HttpStatus.OK).body(income)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addIncome(IncomeDto incomeDto) {
        Optional<Card> optionalCardTo = cardRepository.findById(incomeDto.getToCardId());
        Optional<Card> optionalCardFrom = cardRepository.findById(incomeDto.getFromCardId());
        if (optionalCardTo.isPresent() && optionalCardFrom.isPresent()) {
            incomeRepository.save(new Income(optionalCardFrom.get(), optionalCardTo.get(), incomeDto.getAmount(), incomeDto.getDate()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The card is saved!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "One of card is not found!"));
        }
    }

    public ResponseEntity<Message> editIncome(Long id, IncomeDto incomeDto) {
        Optional<Card> optionalCardTo = cardRepository.findById(incomeDto.getToCardId());
        Optional<Card> optionalCardFrom = cardRepository.findById(incomeDto.getFromCardId());
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent() && optionalCardFrom.isPresent() && optionalCardTo.isPresent()) {
            Income income = optionalIncome.get();
            income.setFromCardId(optionalCardFrom.get());
            income.setToCardId(optionalCardTo.get());
            income.setAmount(income.getAmount());
            income.setDate(income.getDate());
            incomeRepository.save(income);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "Edited!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Not Found!"));
        }
    }

    public ResponseEntity<Message> deleteIncome(Long id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent()) {
            incomeRepository.delete(optionalIncome.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true,"Deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"Not Found!"));
        }
    }
}
