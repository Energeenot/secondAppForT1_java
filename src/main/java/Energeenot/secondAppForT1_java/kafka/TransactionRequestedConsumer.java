package Energeenot.secondAppForT1_java.kafka;

import Energeenot.secondAppForT1_java.dto.TransactionRequestedDTO;
import Energeenot.secondAppForT1_java.service.TransactionRequestedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionRequestedConsumer {
    private final TransactionRequestedService transactionRequestedService;

    @KafkaListener(topics = "t1_demo_transaction_accept", containerFactory = "requestedKafkaListenerContainerFactory", groupId = "transactionRequested")
    public void listen(@Payload TransactionRequestedDTO requestedDTO, Acknowledgment ack) {
        log.info("Received transaction requestedDTO: {}", requestedDTO);
        try {
            transactionRequestedService.processing(requestedDTO);
        }finally {
            ack.acknowledge();
        }

    }

}
