package Energeenot.secondAppForT1_java.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionResultProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic = "t1_demo_transaction_result";

    public void sendTransactionResult(Object value) {
        log.info("Sending transaction result: {}", value);
        kafkaTemplate.send(topic, value);
    }
}
