package br.dev.guilhermecordeiro.bankreconciliation.api.service.parser;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.AccountingTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.domain.BankTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

@Component
class CsvAccountingTransactionFileParser extends AbstractCsvTransactionFileParser<AccountingTransaction> {

    @Override
    protected Function<CSVRecord, AccountingTransaction> mapRecordToEntity() {
        return record -> AccountingTransaction.builder()
                .date(LocalDate.parse(record.get("date")))
                .description(record.get("description"))
                .amount(new BigDecimal(record.get("amount")))
                .type(TransactionTypeEnum.valueOf(record.get("type")))
                .reconciled(false)
                .build();
    }
}
