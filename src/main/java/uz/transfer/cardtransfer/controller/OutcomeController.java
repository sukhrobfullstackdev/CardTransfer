package uz.transfer.cardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.transfer.cardtransfer.entity.Outcome;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.payload.OutcomeDto;
import uz.transfer.cardtransfer.service.OutcomeService;

@RestController
@RequestMapping(value = "/outcome")
public class OutcomeController {
    final OutcomeService outcomeService;

    public OutcomeController(OutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @GetMapping
    public ResponseEntity<Page<Outcome>> getOutcomes(@RequestParam int page, @RequestParam int size) {
        return outcomeService.getOutcomes(page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Outcome> getOutcome(@PathVariable Long id) {
        return outcomeService.getOutcome(id);
    }

    @PostMapping
    public ResponseEntity<Message> addOutcome(@RequestBody OutcomeDto outcomeDto) {
        return outcomeService.addOutcome(outcomeDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editOutcome(@PathVariable Long id, @RequestBody OutcomeDto outcomeDto) {
        return outcomeService.editOutcome(id, outcomeDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteOutcome(@PathVariable Long id) {
        return outcomeService.deleteOutcome(id);
    }
}
