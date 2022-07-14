package uz.transfer.cardtransfer.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OutcomeDto {
    private Long fromCardId;
    private Long toCardId;
    private Long amount;
    private Date date;
    private Double commissionAmount;
}
