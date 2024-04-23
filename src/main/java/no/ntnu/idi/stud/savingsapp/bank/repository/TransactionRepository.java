package no.ntnu.idi.stud.savingsapp.bank.repository;

import no.ntnu.idi.stud.savingsapp.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
