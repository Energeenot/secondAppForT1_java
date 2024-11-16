package Energeenot.secondAppForT1_java.service;

import Energeenot.secondAppForT1_java.dto.TransactionRequestedDTO;
import Energeenot.secondAppForT1_java.dto.TransactionResultMessageDTO;
import Energeenot.secondAppForT1_java.kafka.TransactionResultProducer;
import Energeenot.secondAppForT1_java.model.TransactionRequested;
import Energeenot.secondAppForT1_java.repository.TransactionRequestedRepository;
import Energeenot.secondAppForT1_java.util.TransactionRequestedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionRequestedService {

    private final TransactionRequestedRepository transactionRequestedRepository;
    private final TransactionResultProducer resultProducer;

    @Value("${transaction.max-count}")
    private int maxCount;
    @Value("${transaction.time-window-ms}")
    private long timeWindowSeconds;

    public void processing(TransactionRequestedDTO requestedDTO) {
        log.info("Processing {}", requestedDTO);
        TransactionRequested transactionRequested = TransactionRequestedMapper.toEntity(requestedDTO);
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMinutes(1);
        List<TransactionRequested> recentTransactions = transactionRequestedRepository.findRecentTransactions(
                transactionRequested.getClientId(), transactionRequested.getAccountId(),
                startTime, endTime
        );

        if (recentTransactions.size() >= maxCount) {
            log.info("Max count reached for dto {}", requestedDTO);
            recentTransactions.forEach( t ->{
                resultProducer.sendTransactionResult(toDTO(t, "BLOCKED"));
            });
            return;
        }

        if (transactionRequested.getTransactionAmount() > transactionRequested.getAccountBalance()){
            log.info("transaction amount exceeds account balance");
            resultProducer.sendTransactionResult(toDTO(transactionRequested, "REJECTED"));
            return;
        }

        transactionRequestedRepository.save(transactionRequested);
        resultProducer.sendTransactionResult(toDTO(transactionRequested, "ACCEPTED"));
        log.info("transaction amount succesfully processed");
    }

    private TransactionResultMessageDTO toDTO(TransactionRequested transactionRequested, String status) {
        log.info("processing mapping to message {} with status {}", transactionRequested, status);
        return TransactionResultMessageDTO.builder()
                .transactionId(transactionRequested.getTransactionId())
                .accountId(transactionRequested.getAccountId())
                .clientId(transactionRequested.getClientId())
                .status(status)
                .build();
    }


}
