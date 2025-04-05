package br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.repository;

import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.domain.AccountingTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AccountingTransactionRepository extends ReactiveCrudRepository<AccountingTransaction, Long> {
    Flux<AccountingTransaction> findAllByReconciledFalse();
}
