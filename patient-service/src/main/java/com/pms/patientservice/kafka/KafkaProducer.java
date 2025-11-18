package com.pms.patientservice.kafka;

import com.pms.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void sendEvent(Patient patient, String eventType) {
        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType(eventType)
                .build();

        try{
            kafkaTemplate.send("patient", patientEvent.toByteArray());
        }
        catch (Exception ex){
            log.error("Error sending event to kafka : {}", patientEvent);
        }
    }
}
