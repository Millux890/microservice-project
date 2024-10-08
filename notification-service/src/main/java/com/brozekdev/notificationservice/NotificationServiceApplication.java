package com.brozekdev.notificationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(ConsumerRecord<String, String> record, @Payload(required = false) String payload){

		ObjectMapper objectMapper = new ObjectMapper();
		OrderPlacedEvent order = new OrderPlacedEvent();
		try {
			order = objectMapper.readValue(payload, OrderPlacedEvent.class);
		}catch(Exception e){
			log.error(e.getMessage());
		}


		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");
		System.out.println("Received Notification for Order with id: " + order.getOrderNumber());
		System.out.println("");
		System.out.println("Notification sent to client with id: " + order.getClientId());
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");

}


}
