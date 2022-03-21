package com.pluralsight.webfunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

}
