package br.dev.guilhermecordeiro.bankreconciliation.api.domain;

import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankTransactionTest {

    @Test
    void shouldCreateTransactionUsingBuilder() {
        LocalDate date = LocalDate.of(2024, 10, 10);
        BigDecimal amount = new BigDecimal("123.45");

        BankTransaction tx = BankTransaction.builder()
                .id(1L)
                .date(date)
                .description("Transferência bancária")
                .amount(amount)
                .type(TransactionTypeEnum.DEBIT)
                .reconciled(false)
                .build();

        assertEquals(1L, tx.getId());
        assertEquals(date, tx.getDate());
        assertEquals("Transferência bancária", tx.getDescription());
        assertEquals(amount, tx.getAmount());
        assertEquals(TransactionTypeEnum.DEBIT, tx.getType());
        assertFalse(tx.isReconciled());
    }

    @Test
    void shouldUseGettersAndSetters() {
        BankTransaction tx = new BankTransaction();

        LocalDate date = LocalDate.now();
        BigDecimal amount = new BigDecimal("987.65");

        tx.setId(2L);
        tx.setDate(date);
        tx.setDescription("Depósito");
        tx.setAmount(amount);
        tx.setType(TransactionTypeEnum.CREDIT);
        tx.setReconciled(true);

        assertEquals(2L, tx.getId());
        assertEquals(date, tx.getDate());
        assertEquals("Depósito", tx.getDescription());
        assertEquals(amount, tx.getAmount());
        assertEquals(TransactionTypeEnum.CREDIT, tx.getType());
        assertTrue(tx.isReconciled());
    }

    @Test
    void shouldCreateTransactionUsingAllArgsConstructor() {
        LocalDate date = LocalDate.of(2025, 5, 5);
        BigDecimal amount = new BigDecimal("500.00");

        BankTransaction tx = new BankTransaction(
                3L,
                date,
                "Pagamento de boleto",
                amount,
                TransactionTypeEnum.DEBIT,
                true
        );

        assertNotNull(tx);
        assertEquals(3L, tx.getId());
        assertEquals("Pagamento de boleto", tx.getDescription());
        assertEquals(TransactionTypeEnum.DEBIT, tx.getType());
        assertTrue(tx.isReconciled());
    }
}
