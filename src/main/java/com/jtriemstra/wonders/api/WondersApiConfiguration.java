package com.jtriemstra.wonders.api;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WondersApiConfiguration {
	@Bean
	public Clock defaultClock() {
		return Clock.systemDefaultZone();
	}
}
