package br.dev.guilhermecordeiro.bankreconciliation.api.repository;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.BankTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BankTransactionRepository extends ReactiveCrudRepository<BankTransaction, Long> {}
