package br.dev.guilhermecordeiro.bankreconciliation.api.service.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractCsvTransactionFileParser<T> implements TransactionFileParser<T> {

    protected abstract Function<CSVRecord, T> mapRecordToEntity();

    @Override
    public Mono<List<T>> parse(Part file) {
        if (file instanceof FilePart filePart) {
            return DataBufferUtils.join(filePart.content())
                    .flatMap(dataBuffer -> Mono.fromCallable(() -> {
                        try (InputStream inputStream = dataBuffer.asInputStream(true);
                             InputStreamReader reader = new InputStreamReader(inputStream);
                             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

                            return csvParser.getRecords().stream()
                                    .map(mapRecordToEntity())
                                    .collect(Collectors.toList());

                        } catch (IOException e) {
                            throw new RuntimeException("Falha ao ler arquivo", e);
                        }
                    }).subscribeOn(Schedulers.boundedElastic()));
        } else {
            return Mono.error(new IllegalArgumentException("Tipo de dado incorreto"));
        }
    }

}
