package br.dev.guilhermecordeiro.bankreconciliation.api.dto;

import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionFilterDTOTest {

    @Test
    void shouldCreateTransactionFilterDTOWithAllFields() {
        String description = "Filtrar transações";
        Boolean conciliated = false;
        TransactionTypeEnum type = TransactionTypeEnum.DEBIT;
        BigDecimal minAmount = new BigDecimal("100.00");
        BigDecimal maxAmount = new BigDecimal("500.00");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        TransactionFilterDTO dto = new TransactionFilterDTO(
                description,
                conciliated,
                type,
                minAmount,
                maxAmount,
                startDate,
                endDate
        );

        assertEquals(description, dto.description());
        assertEquals(conciliated, dto.conciliated());
        assertEquals(type, dto.type());
        assertEquals(minAmount, dto.minAmount());
        assertEquals(maxAmount, dto.maxAmount());
        assertEquals(startDate, dto.startDate());
        assertEquals(endDate, dto.endDate());
    }

    @Test
    void shouldAllowNullValues() {
        TransactionFilterDTO dto = new TransactionFilterDTO(
                null, null, null, null, null, null, null
        );

        assertNull(dto.description());
        assertNull(dto.conciliated());
        assertNull(dto.type());
        assertNull(dto.minAmount());
        assertNull(dto.maxAmount());
        assertNull(dto.startDate());
        assertNull(dto.endDate());
    }
}
