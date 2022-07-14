package uz.transfer.cardtransfer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "PLease enter the username!")
    @Column(nullable = false, unique = true)
    private String username;
    @NotNull(message = "PLease enter the number!")
    @Column(nullable = false,unique = true)
    private String number;
    private Long balance;
    @Column(nullable = false)
    private Date expiredDate;
    private boolean active = true;


}
