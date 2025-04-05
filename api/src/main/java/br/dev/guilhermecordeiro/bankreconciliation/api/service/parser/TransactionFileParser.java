package br.dev.guilhermecordeiro.bankreconciliation.api.service.parser;

import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TransactionFileParser<T> {
    Mono<List<T>> parse(Part file);
}
