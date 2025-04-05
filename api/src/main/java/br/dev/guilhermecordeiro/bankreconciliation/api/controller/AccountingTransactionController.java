package br.dev.guilhermecordeiro.bankreconciliation.api.controller;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.AccountingTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.AccountingTransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounting-transactions")
public class AccountingTransactionController extends AbstractTransactionController<AccountingTransaction> {

    public AccountingTransactionController(AccountingTransactionService service) {
        super(service);
    }
}
