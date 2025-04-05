package br.dev.guilhermecordeiro.bankreconciliation.api.service.parser;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.BankTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

@Component
class CsvAccountingTransactionFileParser extends AbstractCsvTransactionFileParser<BankTransaction> {

    @Override
    protected Function<CSVRecord, BankTransaction> mapRecordToEntity() {
        return record -> BankTransaction.builder()
                .date(LocalDate.parse(record.get("date")))
                .description(record.get("description"))
                .amount(new BigDecimal(record.get("amount")))
                .type(TransactionTypeEnum.valueOf(record.get("type")))
                .reconciled(false)
                .build();
    }
}
