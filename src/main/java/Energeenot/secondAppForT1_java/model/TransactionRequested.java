package Energeenot.secondAppForT1_java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_requested")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequested extends AbstractPersistable<Long> {

    @Column(name = "client_id")
    private String clientId;
    @Column(name = "account_id")
    private String accountId;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Column(name = "transaction_amount")
    private double transactionAmount;
    @Column(name = "account_balance")
    private double accountBalance;
}
