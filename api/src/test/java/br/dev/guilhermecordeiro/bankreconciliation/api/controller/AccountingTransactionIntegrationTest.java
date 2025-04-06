package br.dev.guilhermecordeiro.bankreconciliation.api.controller;

import br.dev.guilhermecordeiro.bankreconciliation.api.IntegrationTestBase;
import br.dev.guilhermecordeiro.bankreconciliation.api.domain.AccountingTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountingTransactionIntegrationTest extends IntegrationTestBase {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @Test
    void shouldUploadFileAndQueryTransactions() throws Exception {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

        Path tempFile = Files.createTempFile("test-transactions", ".csv");
        Files.writeString(tempFile, """
                date,description,amount,type
                2024-01-01,Transação A,100.00,CREDIT
                2024-01-02,Transação B,200.00,DEBIT
                """);

        MultiValueMap<String, Object> multipartData = new LinkedMultiValueMap<>();
        multipartData.add("file", new FileSystemResource(tempFile.toFile()));

        client.post()
                .uri("/accounting-transactions/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(multipartData)
                .exchange()
                .expectStatus().isOk();

        List<AccountingTransaction> transactions = client.get()
                .uri("/accounting-transactions")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AccountingTransaction.class)
                .returnResult()
                .getResponseBody();

        assertThat(transactions)
                .isNotNull()
                .hasSize(2)
                .extracting(AccountingTransaction::getDescription)
                .contains("Transação A", "Transação B");

        Files.deleteIfExists(tempFile);
    }
}
