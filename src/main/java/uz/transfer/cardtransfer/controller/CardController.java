package uz.transfer.cardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.transfer.cardtransfer.entity.Card;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.service.CardService;

@RestController
@RequestMapping(value = "/card")
public class CardController {
    final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<Page<Card>> getCards(@RequestParam int page, @RequestParam int size) {
        return cardService.getCards(page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Card> getCard(@PathVariable Long id) {
        return cardService.getCard(id);
    }

    @PostMapping
    public ResponseEntity<Message> addCard(@RequestBody Card card) {
        return cardService.addCard(card);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editCard(@PathVariable Long id, @RequestBody Card card) {
        return cardService.editCard(id, card);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteCard(@PathVariable Long id) {
        return cardService.deleteCard(id);
    }
}
