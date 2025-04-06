package br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.service;

import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.domain.AccountingTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.domain.BankTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.enums.TransactionTypeEnum;
import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.repository.AccountingTransactionRepository;
import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.repository.BankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReconciliationServiceTest {

    private BankTransactionRepository bankRepo;
    private AccountingTransactionRepository accRepo;
    private ReconciliationService reconciliationService;

    @BeforeEach
    void setUp() {
        bankRepo = mock(BankTransactionRepository.class);
        accRepo = mock(AccountingTransactionRepository.class);
        reconciliationService = new ReconciliationService(bankRepo, accRepo);
    }

    @Test
    void shouldReconcileMatchingTransactions() {
        BankTransaction bankTx = BankTransaction.builder()
                .id(1L)
                .date(LocalDate.now())
                .amount(BigDecimal.valueOf(100))
                .type(TransactionTypeEnum.DEBIT)
                .reconciled(false)
                .build();

        AccountingTransaction accTx = AccountingTransaction.builder()
                .id(1L)
                .date(LocalDate.now())
                .amount(BigDecimal.valueOf(100))
                .type(TransactionTypeEnum.DEBIT)
                .reconciled(false)
                .build();

        when(bankRepo.findAllByReconciledFalse()).thenReturn(Flux.just(bankTx));
        when(accRepo.findAllByReconciledFalse()).thenReturn(Flux.just(accTx));
        when(bankRepo.save(any())).thenReturn(Mono.just(bankTx));
        when(accRepo.save(any())).thenReturn(Mono.just(accTx));

        reconciliationService.reconcile().block();

        verify(bankRepo, times(1)).save(argThat(BankTransaction::isReconciled));
        verify(accRepo, times(1)).save(argThat(AccountingTransaction::isReconciled));
    }
}
