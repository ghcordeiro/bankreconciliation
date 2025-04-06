package br.dev.guilhermecordeiro.bankreconciliation.api.controller;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.AccountingTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.AccountingTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@WebFluxTest(AccountingTransactionController.class)
class AccountingTransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AccountingTransactionService service;

    private AccountingTransaction mockTransaction;

    @BeforeEach
    void setup() {
        mockTransaction = new AccountingTransaction();
        mockTransaction.setId(1L);
        mockTransaction.setDescription("Teste contábil");
        mockTransaction.setAmount(BigDecimal.valueOf(150.75));
    }

    @Test
    void shouldListFilteredTransactions() {
        Mockito.when(service.findByFilter(Mockito.any()))
                .thenReturn(Mono.just(List.of(mockTransaction)));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounting-transactions")
                        .queryParam("description", "Teste")
                        .queryParam("reconciled", "false")
                        .queryParam("type", TransactionTypeEnum.DEBIT)
                        .queryParam("minAmount", 100)
                        .queryParam("maxAmount", 200)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].description").isEqualTo("Teste contábil")
                .jsonPath("$[0].amount").isEqualTo(150.75)
                .jsonPath("$[0].reconciled").isEqualTo(false);
    }

    @Test
    void shouldUploadFileSuccessfully() {
        Mockito.when(service.processFile(Mockito.any()))
                .thenReturn(Mono.empty());

        var bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ClassPathResource("transaction-test.csv"))
                .header("Content-Type", "text/csv");

        webTestClient.post()
                .uri("/accounting-transactions/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().isOk();
    }
}
