package br.dev.guilhermecordeiro.bankreconciliation.api.service;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.AccountingTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.repository.AccountingTransactionRepository;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.parser.TransactionFileParser;
import org.springframework.stereotype.Service;

@Service
public class AccountingTransactionService extends AbstractTransactionService<AccountingTransaction> {

    public AccountingTransactionService(TransactionFileParser<AccountingTransaction> parser,
                                        AccountingTransactionRepository repository) {
        super(parser, repository);
    }
}
