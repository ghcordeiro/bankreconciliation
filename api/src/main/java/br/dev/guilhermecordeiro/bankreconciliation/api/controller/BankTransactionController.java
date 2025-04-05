package br.dev.guilhermecordeiro.bankreconciliation.api.controller;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.BankTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.BankTransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-transactions")
public class BankTransactionController extends AbstractTransactionController<BankTransaction> {

    public BankTransactionController(BankTransactionService service) {
        super(service);
    }
}
