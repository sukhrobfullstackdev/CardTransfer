package uz.transfer.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.transfer.cardtransfer.entity.Card;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.repository.CardRepository;

import java.util.Optional;

@Service
public class CardService {
    final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<Page<Card>> getCards(int page, int size) {
        return ResponseEntity.ok(cardRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Card> getCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.map(card -> ResponseEntity.status(HttpStatus.OK).body(card)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addCard(Card card) {
        boolean exists = cardRepository.existsByUsernameOrNumber(card.getUsername(), card.getNumber());
        if (!exists) {
            cardRepository.save(card);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The card is successfully added!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "This username or number is already exists!"));
        }
    }

    public ResponseEntity<Message> editCard(Long id, Card card) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            Card editingCard = optionalCard.get();
            editingCard.setUsername(card.getUsername());
            editingCard.setNumber(card.getNumber());
            editingCard.setExpiredDate(card.getExpiredDate());
            editingCard.setBalance(card.getBalance());
            editingCard.setActive(card.isActive());
            cardRepository.save(editingCard);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The card was successfully edited!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The card is not found!"));
        }
    }

    public ResponseEntity<Message> deleteCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            cardRepository.delete(optionalCard.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true,"The card was successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"The card is not found!"));
        }
    }
}
