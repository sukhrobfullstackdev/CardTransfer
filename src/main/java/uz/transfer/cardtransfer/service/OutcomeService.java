package uz.transfer.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.transfer.cardtransfer.entity.Card;
import uz.transfer.cardtransfer.entity.Outcome;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.payload.OutcomeDto;
import uz.transfer.cardtransfer.repository.CardRepository;
import uz.transfer.cardtransfer.repository.OutcomeRepository;

import java.util.Optional;

@Service
public class OutcomeService {
    final OutcomeRepository outcomeRepository;
    final CardRepository cardRepository;

    public OutcomeService(OutcomeRepository outcomeRepository, CardRepository cardRepository) {
        this.outcomeRepository = outcomeRepository;
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<Page<Outcome>> getOutcomes(int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Card> cardByUsernameOptional = cardRepository.findCardByUsername(String.valueOf(principal));
        return cardByUsernameOptional.map(card -> ResponseEntity.ok(outcomeRepository.findAllByFromCardId(PageRequest.of(page, size), card))).orElse(null);
    }

    public ResponseEntity<Outcome> getOutcome(Long id) {
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        return optionalOutcome.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addOutcome(OutcomeDto outcomeDto) {
        Optional<Card> optionalCardTo = cardRepository.findById(outcomeDto.getToCardId());
        Optional<Card> optionalCardFrom = cardRepository.findById(outcomeDto.getFromCardId());
        if (optionalCardFrom.isPresent() && optionalCardTo.isPresent()) {
            outcomeRepository.save(new Outcome(optionalCardFrom.get(), optionalCardTo.get(), outcomeDto.getAmount(), outcomeDto.getDate(), outcomeDto.getCommissionAmount()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "Saved!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Not Found!"));
        }
    }

    public ResponseEntity<Message> editOutcome(Long id, OutcomeDto outcomeDto) {
        Optional<Card> optionalCardTo = cardRepository.findById(outcomeDto.getToCardId());
        Optional<Card> optionalCardFrom = cardRepository.findById(outcomeDto.getFromCardId());
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        if (optionalOutcome.isPresent() && optionalCardTo.isPresent() && optionalCardFrom.isPresent()) {
            Outcome outcome = optionalOutcome.get();
            outcome.setFromCardId(optionalCardFrom.get());
            outcome.setToCardId(optionalCardTo.get());
            outcome.setAmount(outcomeDto.getAmount());
            outcome.setDate(outcomeDto.getDate());
            outcome.setCommissionAmount(outcomeDto.getCommissionAmount());
            outcomeRepository.save(outcome);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "Edited!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Not Found!"));
        }
    }

    public ResponseEntity<Message> deleteOutcome(Long id) {
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        if (optionalOutcome.isPresent()) {
            outcomeRepository.delete(optionalOutcome.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "Deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Not Found!"));
        }
    }
}
