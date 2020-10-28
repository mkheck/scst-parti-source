package com.thehecklers.saksource;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@SpringBootApplication
public class SakSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SakSourceApplication.class, args);
	}

}

@Configuration
class ConfigThisThing {
	private Long counter = 1L;

	@Bean
	Supplier<Greeting> sendGreeting() {
		return () -> {
			Greeting greeting = new Greeting(counter++,
					"Greetings and salutations.");

			System.out.println(greeting);

			return greeting;
		};
	}
}

@Component
class PartitionKeyBS implements PartitionKeyExtractorStrategy {
	@Override
	public Object extractKey(Message<?> message) {
		return ((Greeting) message.getPayload()).getId();
	}
}

@Data
@AllArgsConstructor
class Greeting {
	private final Long id;
	private final String text;
}