package br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.service;


import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.domain.AccountingTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.domain.BankTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.repository.AccountingTransactionRepository;
import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.repository.BankTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReconciliationService {

    private final BankTransactionRepository bankRepo;
    private final AccountingTransactionRepository accRepo;

    public Mono<Void> reconcile() {
        return Mono.zip(
                        bankRepo.findAllByReconciledFalse().collectList(),
                        accRepo.findAllByReconciledFalse().collectList()
                )
                .flatMap(tuple -> {
                    List<BankTransaction> banks = tuple.getT1();
                    List<AccountingTransaction> accs = tuple.getT2();

                    for (BankTransaction bank : banks) {
                        AccountingTransaction match = accs.stream()
                                .filter(a -> !a.isReconciled())
                                .filter(a -> isMatch(bank, a))
                                .findFirst()
                                .orElse(null);

                        if (match != null) {
                            bank.setReconciled(true);
                            match.setReconciled(true);

                            bankRepo.save(bank).subscribe();
                            accRepo.save(match).subscribe();
                        }
                    }

                    return Mono.empty();
                });
    }

    private boolean isMatch(BankTransaction bank, AccountingTransaction acc) {
        LocalDate date1 = bank.getDate();
        LocalDate date2 = acc.getDate();

        return bank.getAmount().compareTo(acc.getAmount()) == 0
                && bank.getType() == acc.getType()
                && !date1.isBefore(date2.minusDays(2))
                && !date1.isAfter(date2.plusDays(2));
    }
}
