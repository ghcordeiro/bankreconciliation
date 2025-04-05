package br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.scheduler;

import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.service.ReconciliationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReconciliationJob {


    private final ReconciliationService reconciliationService;

    @Scheduled(fixedDelay = 60000)
    public void runJob() {
        log.info("Start job at "+ LocalDateTime.now());
        reconciliationService.reconcile().subscribe();
    }
}
