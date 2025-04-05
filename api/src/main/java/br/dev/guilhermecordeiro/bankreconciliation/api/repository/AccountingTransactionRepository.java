package br.dev.guilhermecordeiro.bankreconciliation.api.repository;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.AccountingTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountingTransactionRepository extends ReactiveCrudRepository<AccountingTransaction, Long> {}
