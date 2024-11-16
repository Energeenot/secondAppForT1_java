package Energeenot.secondAppForT1_java.util;

import Energeenot.secondAppForT1_java.dto.TransactionRequestedDTO;
import Energeenot.secondAppForT1_java.model.TransactionRequested;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionRequestedMapper {

    public static TransactionRequested toEntity(TransactionRequestedDTO dto) {
        return TransactionRequested.builder()
                .clientId(dto.getClientId())
                .accountId(dto.getAccountId())
                .transactionId(dto.getTransactionId())
                .timestamp(dto.getTimestamp())
                .transactionAmount(dto.getTransactionAmount())
                .accountBalance(dto.getAccountBalance())
                .build();
    }
}
