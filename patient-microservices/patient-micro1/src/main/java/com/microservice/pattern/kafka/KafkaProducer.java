package com.microservice.pattern.kafka;

import com.microservice.pattern.model.Patient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaProducer {


    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final String bootstrapServers;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate,
                         @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        this.kafkaTemplate = kafkaTemplate;
        this.bootstrapServers = bootstrapServers;
    }


    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try {
            CompletableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send("patient", event.toByteArray());

            future.thenAccept(result -> {
                log.info("PatientCreated event sent to Kafka topic={} partition={} offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }).exceptionally(ex -> {
                log.error("Failed to send PatientCreated event for patientId={}: {}",
                        patient.getId(), ex.getMessage(), ex);
                return null;
            });

            // Wait for completion with timeout
            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error sending PatientCreated event for patientId={}: {}",
                    patient.getId(), e.getMessage(), e);
        }
        //https://www.base64decode.org/

    }

    @PostConstruct
    public void logKafkaConfig() {
        log.info("KafkaTemplate configured to use ByteArraySerializer for values");
        log.info("Kafka bootstrap servers = {}", bootstrapServers);
    }
}
