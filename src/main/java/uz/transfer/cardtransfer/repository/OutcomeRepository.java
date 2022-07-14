package uz.transfer.cardtransfer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.transfer.cardtransfer.entity.Card;
import uz.transfer.cardtransfer.entity.Income;
import uz.transfer.cardtransfer.entity.Outcome;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome,Long> {
    Page<Outcome> findAllByFromCardId(Pageable pageable, Card fromCardId);
}
