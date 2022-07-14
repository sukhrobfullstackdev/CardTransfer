package uz.transfer.cardtransfer.controller;

import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.transfer.cardtransfer.entity.Income;
import uz.transfer.cardtransfer.payload.IncomeDto;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.service.IncomeService;

@RestController
@RequestMapping(value = "/income")
public class IncomeController {
    final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public ResponseEntity<Page<Income>> getIncomes(@RequestParam int page, @RequestParam int size) {
        return incomeService.getIncomes(page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Income> getIncome(@PathVariable Long id) {
        return incomeService.getIncome(id);
    }

    @PostMapping
    public ResponseEntity<Message> addIncome(@RequestBody IncomeDto incomeDto) {
        return incomeService.addIncome(incomeDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editIncome(@RequestBody IncomeDto incomeDto, @PathVariable Long id) {
        return incomeService.editIncome(id, incomeDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteIncome(@PathVariable Long id) {
        return incomeService.deleteIncome(id);
    }
}
