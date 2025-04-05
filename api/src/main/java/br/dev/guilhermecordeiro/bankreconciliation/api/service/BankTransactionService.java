package br.dev.guilhermecordeiro.bankreconciliation.api.service;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.BankTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.repository.BankTransactionRepository;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.parser.TransactionFileParser;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionService extends AbstractTransactionService<BankTransaction> {

    public BankTransactionService(TransactionFileParser<BankTransaction> parser,
                                  BankTransactionRepository repository) {
        super(parser, repository);
    }
}
