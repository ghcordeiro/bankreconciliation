package br.dev.guilhermecordeiro.bankreconciliation.api.domain;

import br.dev.guilhermecordeiro.bankreconciliation.api.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("bank_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransaction implements FilterableTransaction {

    @Id
    private Long id;

    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private TransactionTypeEnum type;
    private boolean reconciled;
}
