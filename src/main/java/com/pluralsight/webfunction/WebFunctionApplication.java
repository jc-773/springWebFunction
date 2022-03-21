package com.pluralsight.webfunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFunctionApplication.class, args);
	}

	List<TollStation> tollStations;

	public WebFunctionApplication() {
		tollStations = new ArrayList<TollStation>();
		tollStations.add((new TollStation("100A", 112.5f, 2)));
		tollStations.add((new TollStation("111C", 114f, 4)));
		tollStations.add((new TollStation("112C", 126f, 2)));
		
	}

	@Bean
	public Function<String, TollStation> retreiveStation() {
		return value -> {
			System.out.println("received request for station - " + value);
			return tollStations.stream()
			.filter(toll -> value.equals((toll.getStationId())))
			.findAny()
			.orElse(null);

		};
	}

	@Bean
	public Consumer<TollRecord> processTollRecord() {
		return value -> {
			System.out.println(value.getLicensePlate());
		};
	}

	@Bean
	public Function<TollRecord, Mono<String>> processTollRecordReactively() {
		return value -> {
			System.out.println(value.getLicensePlate());
			return Mono.empty();
		};
	}

	@Bean
	public Consumer<Flux<TollRecord>> processMultipleTollRecords() {
		return value -> {
			value.subscribe(tolls -> System.out.println(tolls.getLicensePlate()));
		};
	}

	@Bean
	public Supplier <Flux<TollStation>> getAllTollStations() {
		return() -> Flux.fromIterable(tollStations);
	}

}
