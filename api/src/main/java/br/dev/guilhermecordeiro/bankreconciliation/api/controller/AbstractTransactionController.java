package br.dev.guilhermecordeiro.bankreconciliation.api.controller;

import br.dev.guilhermecordeiro.bankreconciliation.api.domain.FilterableTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.dto.TransactionFilterDTO;
import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.AbstractTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractTransactionController<T extends FilterableTransaction> {

    private final AbstractTransactionService<T> service;

    @PostMapping("/upload")
    public Mono<ResponseEntity<Void>> upload(@RequestPart("file") Part file) {
        return service.processFile(file)
                .thenReturn(ResponseEntity.ok().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<T>>> findBankTransactions(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean reconciled,
            @RequestParam(required = false) TransactionTypeEnum type,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        var params = new TransactionFilterDTO(description, reconciled, type, minAmount, maxAmount, startDate, endDate);
        return service.findByFilter(params)
                .map(ResponseEntity::ok);
    }
}
