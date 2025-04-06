package br.dev.guilhermecordeiro.bankreconciliation.api.service.parser;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.BankTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class CsvBankransactionFileParserTest {

    private CsvBankTransactionFileParser parser;

    @BeforeEach
    void setUp() {
        parser = new CsvBankTransactionFileParser();
    }

    @Test
    void shouldParseCsvFileCorrectly() {
        String csvContent = """
                date,description,amount,type
                2024-04-01,Compra no mercado,150.50,DEBIT
                2024-04-02,Salário,3000.00,CREDIT
                """;

        DataBuffer buffer = new DefaultDataBufferFactory().wrap(csvContent.getBytes(StandardCharsets.UTF_8));

        FilePart filePart = Mockito.mock(FilePart.class);
        Mockito.when(filePart.content()).thenReturn(Flux.just(buffer));

        StepVerifier.create(parser.parse(filePart))
                .assertNext(transactions -> {
                    assertThat(transactions).hasSize(2);

                    BankTransaction t1 = transactions.get(0);
                    assertThat(t1.getDate()).hasToString("2024-04-01");
                    assertThat(t1.getDescription()).isEqualTo("Compra no mercado");
                    assertThat(t1.getAmount()).isEqualTo("150.50");
                    assertThat(t1.getType()).isEqualTo(TransactionTypeEnum.DEBIT);
                    assertThat(t1.isReconciled()).isFalse();

                    BankTransaction t2 = transactions.get(1);
                    assertThat(t2.getDescription()).isEqualTo("Salário");
                    assertThat(t2.getType()).isEqualTo(TransactionTypeEnum.CREDIT);
                })
                .verifyComplete();
    }
}
