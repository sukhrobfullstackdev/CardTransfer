package uz.transfer.cardtransfer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Card fromCardId;
    @ManyToOne(optional = false)
    private Card toCardId;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Double commissionAmount;

    public Outcome(Card fromCardId, Card toCardId, Long amount, Date date, Double commissionAmount) {
        this.fromCardId = fromCardId;
        this.toCardId = toCardId;
        this.amount = amount;
        this.date = date;
        this.commissionAmount = commissionAmount;
    }
}
