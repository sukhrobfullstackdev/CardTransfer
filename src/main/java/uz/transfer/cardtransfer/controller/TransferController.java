package uz.transfer.cardtransfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.payload.TransferDto;
import uz.transfer.cardtransfer.service.TransferService;

@RestController
@RequestMapping(value = "/transfer")
public class TransferController {
    final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Message> transfer(@RequestBody TransferDto transferDto) {
        return transferService.transfer(transferDto);
    }
}
