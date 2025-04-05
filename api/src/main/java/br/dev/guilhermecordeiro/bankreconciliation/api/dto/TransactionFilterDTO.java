package br.dev.guilhermecordeiro.bankreconciliation.api.dto;

import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionFilterDTO(
        String description,
        Boolean conciliated,
        TransactionTypeEnum type,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        LocalDate startDate,
        LocalDate endDate
) {}