package uz.transfer.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.transfer.cardtransfer.controller.CardController;
import uz.transfer.cardtransfer.controller.IncomeController;
import uz.transfer.cardtransfer.controller.OutcomeController;
import uz.transfer.cardtransfer.entity.Card;
import uz.transfer.cardtransfer.entity.Income;
import uz.transfer.cardtransfer.entity.Outcome;
import uz.transfer.cardtransfer.payload.IncomeDto;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.payload.OutcomeDto;
import uz.transfer.cardtransfer.payload.TransferDto;
import uz.transfer.cardtransfer.repository.CardRepository;
import uz.transfer.cardtransfer.repository.IncomeRepository;
import uz.transfer.cardtransfer.repository.OutcomeRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransferService {
    final CardRepository cardRepository;
    final IncomeController incomeController;
    final IncomeRepository incomeRepository;
    final OutcomeRepository outcomeRepository;
    final CardController cardController;

    public TransferService(IncomeRepository incomeRepository,CardRepository cardRepository, IncomeController incomeController, OutcomeRepository outcomeRepository, CardController cardController) {
        this.cardRepository = cardRepository;
        this.incomeController = incomeController;
        this.outcomeRepository = outcomeRepository;
        this.cardController = cardController;
        this.incomeRepository = incomeRepository;
    }

    public ResponseEntity<Message> transfer(TransferDto transferDto) {
        double percent = 0.05;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Card> cardByUsernameOptional = cardRepository.findCardByUsername(String.valueOf(principal));
        Optional<Card> cardByNumberOptional = cardRepository.findCardByNumber(transferDto.getNumber());
        if (cardByUsernameOptional.isPresent() && cardByNumberOptional.isPresent()) {
            Card cardByNumber = cardByNumberOptional.get();
            Card cardByUsername = cardByUsernameOptional.get();
            if ((cardByUsername.getBalance() > transferDto.getBalance() + transferDto.getBalance() * percent) && cardByNumber.isActive() && cardByUsername.isActive()) {
                incomeRepository.save(new Income(cardByUsernameOptional.get(),cardByNumberOptional.get(),transferDto.getBalance(),new Date(System.currentTimeMillis())));
                outcomeRepository.save(new Outcome(cardByUsernameOptional.get(),cardByUsernameOptional.get(),transferDto.getBalance(),new Date(System.currentTimeMillis()),percent));
                //incomeController.addIncome(new IncomeDto(cardByUsername.getId(), cardByNumber.getId(), transferDto.getBalance(), new Date(System.currentTimeMillis())));
                //outcomeController.addOutcome(new OutcomeDto(cardByUsername.getId(), cardByNumber.getId(), transferDto.getBalance(), new Date(System.currentTimeMillis()), transferDto.getBalance() * percent));
                cardByUsername.setBalance((long) (cardByUsername.getBalance() - (transferDto.getBalance() + transferDto.getBalance() * percent)));
                cardByNumber.setBalance(cardByNumber.getBalance() + transferDto.getBalance());
                cardController.editCard(cardByUsername.getId(), cardByUsername);
                cardController.editCard(cardByNumber.getId(), cardByNumber);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The money was successfully transferred!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Not enough money!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "One of card is not found"));
        }
    }
}
