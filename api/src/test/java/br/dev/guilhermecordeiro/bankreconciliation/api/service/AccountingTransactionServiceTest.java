package br.dev.guilhermecordeiro.bankreconciliation.api.service;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.AccountingTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.dto.TransactionFilterDTO;
import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import br.dev.guilhermecordeiro.bankreconciliation.api.repository.AccountingTransactionRepository;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.parser.TransactionFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountingTransactionServiceTest {

    private TransactionFileParser<AccountingTransaction> parser;
    private AccountingTransactionRepository repository;
    private AccountingTransactionService service;

    @BeforeEach
    void setup() {
        parser = mock(TransactionFileParser.class);
        repository = mock(AccountingTransactionRepository.class);
        service = new AccountingTransactionService(parser, repository);
    }

    @Test
    void shouldProcessFileAndSaveTransactions() {
        Part mockFile = mock(Part.class);
        List<AccountingTransaction> transactions = List.of(
                AccountingTransaction.builder().description("Transação A").build(),
                AccountingTransaction.builder().description("Transação B").build()
        );

        when(parser.parse(mockFile)).thenReturn(Mono.just(transactions));
        when(repository.saveAll(transactions)).thenReturn(Flux.fromIterable(transactions));

        StepVerifier.create(service.processFile(mockFile))
                .verifyComplete();

        verify(parser).parse(mockFile);
        verify(repository).saveAll(transactions);
    }

    @Test
    void shouldFilterTransactionsByDescription() {
        var tx1 = AccountingTransaction.builder()
                .description("Salário")
                .amount(BigDecimal.valueOf(3000))
                .date(LocalDate.of(2024, 4, 2))
                .type(TransactionTypeEnum.CREDIT)
                .reconciled(true)
                .build();

        var tx2 = AccountingTransaction.builder()
                .description("Mercado")
                .amount(BigDecimal.valueOf(150))
                .date(LocalDate.of(2024, 4, 1))
                .type(TransactionTypeEnum.DEBIT)
                .reconciled(false)
                .build();

        when(repository.findAll()).thenReturn(Flux.just(tx1, tx2));

        var filter = new TransactionFilterDTO(
                "Salário",
                null,
                null,
                null,
                null,
                null,
                null
        );

        Mono<List<AccountingTransaction>> result = service.findByFilter(filter);

        StepVerifier.create(result)
                .assertNext(list -> {
                    assertThat(list).hasSize(1);
                    assertThat(list.get(0).getDescription()).isEqualTo("Salário");
                })
                .verifyComplete();
    }
}
