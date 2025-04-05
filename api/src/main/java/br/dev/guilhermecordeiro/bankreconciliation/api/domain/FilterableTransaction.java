package br.dev.guilhermecordeiro.bankreconciliation.api.domain;

import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FilterableTransaction {
    boolean isReconciled();
    TransactionTypeEnum getType();
    BigDecimal getAmount();
    LocalDate getDate();

    String getDescription();
}