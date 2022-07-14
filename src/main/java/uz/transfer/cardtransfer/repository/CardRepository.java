package uz.transfer.cardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.transfer.cardtransfer.entity.Card;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByUsernameOrNumber(String username, String number);

    boolean existsByUsernameOrNumberAndIdNot(String username, String number, Long id);
    Optional<Card> findCardByUsername(String username);
    Optional<Card> findCardByNumber(String number);
}
