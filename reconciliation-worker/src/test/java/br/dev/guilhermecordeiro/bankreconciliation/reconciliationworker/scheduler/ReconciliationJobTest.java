package br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.scheduler;

import br.dev.guilhermecordeiro.bankreconciliation.reconciliationworker.service.ReconciliationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReconciliationJobTest {

    private ReconciliationService reconciliationService;
    private ReconciliationJob reconciliationJob;

    @BeforeEach
    void setUp() {
        reconciliationService = mock(ReconciliationService.class);
        reconciliationJob = new ReconciliationJob(reconciliationService);
    }

    @Test
    void shouldInvokeReconcileMethodWhenJobRuns() {
        when(reconciliationService.reconcile()).thenReturn(Mono.empty());

        reconciliationJob.runJob();

        verify(reconciliationService, times(1)).reconcile();
    }
}
