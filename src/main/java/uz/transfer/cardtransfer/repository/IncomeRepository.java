package uz.transfer.cardtransfer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.transfer.cardtransfer.entity.Card;
import uz.transfer.cardtransfer.entity.Income;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Page<Income> findAllByFromCardId(Pageable pageable,Card fromCardId);
}
