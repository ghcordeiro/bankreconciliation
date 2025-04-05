package br.dev.guilhermecordeiro.bankreconciliation.api.service;


import br.dev.guilhermecordeiro.bankreconciliation.api.domain.FilterableTransaction;
import br.dev.guilhermecordeiro.bankreconciliation.api.dto.TransactionFilterDTO;
import br.dev.guilhermecordeiro.bankreconciliation.api.service.parser.TransactionFileParser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

public abstract class AbstractTransactionService<T extends FilterableTransaction> {

    private final TransactionFileParser<T> parser;
    private final ReactiveCrudRepository<T, Long> repository;

    protected AbstractTransactionService(TransactionFileParser<T> parser,
                                         ReactiveCrudRepository<T, Long> repository) {
        this.parser = parser;
        this.repository = repository;
    }

    public Mono<Void> processFile(Part file) {
        return parser.parse(file)
                .flatMapMany(repository::saveAll)
                .then();
    }

    public Mono<List<T>> findByFilter(TransactionFilterDTO dto) {
        return repository.findAll()
                .filter(tx -> dto.description() == null || Objects.equals(tx.getDescription(), dto.description()))
                .filter(tx -> dto.conciliated() == null || tx.isReconciled() == dto.conciliated())
                .filter(tx -> dto.type() == null || tx.getType() == dto.type())
                .filter(tx -> dto.minAmount() == null || tx.getAmount().compareTo(dto.minAmount()) >= 0)
                .filter(tx -> dto.maxAmount() == null || tx.getAmount().compareTo(dto.maxAmount()) <= 0)
                .filter(tx -> dto.startDate() == null || !tx.getDate().isBefore(dto.startDate()))
                .filter(tx -> dto.endDate() == null || !tx.getDate().isAfter(dto.endDate()))
                .collectList();
    }
}