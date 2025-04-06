package br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.domain;

import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.enums.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountingTransactionTest {

    @Test
    void shouldCreateTransactionUsingBuilder() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        BigDecimal amount = new BigDecimal("250.50");

        AccountingTransaction tx = AccountingTransaction.builder()
                .id(1L)
                .date(date)
                .description("Teste Builder")
                .amount(amount)
                .type(TransactionTypeEnum.CREDIT)
                .reconciled(true)
                .build();

        assertEquals(1L, tx.getId());
        assertEquals(date, tx.getDate());
        assertEquals("Teste Builder", tx.getDescription());
        assertEquals(amount, tx.getAmount());
        assertEquals(TransactionTypeEnum.CREDIT, tx.getType());
        assertTrue(tx.isReconciled());
    }

    @Test
    void shouldUseGettersAndSetters() {
        AccountingTransaction tx = new AccountingTransaction();

        LocalDate date = LocalDate.now();
        BigDecimal amount = new BigDecimal("99.99");

        tx.setId(2L);
        tx.setDate(date);
        tx.setDescription("Teste Manual");
        tx.setAmount(amount);
        tx.setType(TransactionTypeEnum.DEBIT);
        tx.setReconciled(false);

        assertEquals(2L, tx.getId());
        assertEquals(date, tx.getDate());
        assertEquals("Teste Manual", tx.getDescription());
        assertEquals(amount, tx.getAmount());
        assertEquals(TransactionTypeEnum.DEBIT, tx.getType());
        assertFalse(tx.isReconciled());
    }

    @Test
    void shouldCreateTransactionUsingAllArgsConstructor() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        BigDecimal amount = new BigDecimal("1000.00");

        AccountingTransaction tx = new AccountingTransaction(
                3L,
                date,
                "Transação Completa",
                amount,
                TransactionTypeEnum.CREDIT,
                true
        );

        assertNotNull(tx);
        assertEquals(3L, tx.getId());
        assertEquals("Transação Completa", tx.getDescription());
        assertEquals(TransactionTypeEnum.CREDIT, tx.getType());
        assertTrue(tx.isReconciled());
    }
}
