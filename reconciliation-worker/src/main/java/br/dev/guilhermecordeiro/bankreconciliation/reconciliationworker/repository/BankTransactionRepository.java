package br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.repository;

import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.domain.BankTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BankTransactionRepository extends ReactiveCrudRepository<BankTransaction, Long> {
    Flux<BankTransaction> findAllByReconciledFalse();
}
