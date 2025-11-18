package com.pms.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaConsumer {
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // Any logic related to analytics
            log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}, EventType={}]",
                    patientEvent.getPatientId(),patientEvent.getName(),patientEvent.getEmail(),patientEvent.getEventType());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error while parsing event {}", e.getMessage());
        }
    }
}
