package com.hotel.hotelsearchdemo.ratelimiter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class RateLimiterConfigTest {

	RateLimiterConfig rateLimiterConfig = null;

	@Before
	public void setUp() {
		rateLimiterConfig = RateLimiterConfig.builder()
				.apiConfigs(Arrays.asList(
						RateLimiterConfig.ApiConfig.builder()
								.apiKey("api_1")
								.apiRateConfig(RateLimiterConfig.RateConfig.builder()
										.limitRequest(BigInteger.ONE)
										.perSeconds(5)
										.waitSeconds(60)
										.build())
								.build(),
						RateLimiterConfig.ApiConfig.builder()
								.apiKey("api_2")
								.apiRateConfig(RateLimiterConfig.RateConfig.builder()
										.limitRequest(BigInteger.valueOf(2))
										.perSeconds(5)
										.waitSeconds(60)
										.build()).build(),
						RateLimiterConfig.ApiConfig.builder()
								.apiKey("api_3")
								.apiRateConfig(RateLimiterConfig.RateConfig.builder()
										.limitRequest(BigInteger.valueOf(3))
										.perSeconds(5)
										.waitSeconds(60)
										.build()).build()))
				.defaultRateConfig(RateLimiterConfig.RateConfig.builder()
						.limitRequest(BigInteger.TEN)
						.perSeconds(5)
						.waitSeconds(60)
						.build())
				.build();
	}

	@Test
	public void getRateConfigFor_present_test() {
		RateLimiterConfig.RateConfig expected = RateLimiterConfig.RateConfig.builder()
				.limitRequest(BigInteger.valueOf(3))
				.perSeconds(5)
				.waitSeconds(60)
				.build();

		RateLimiterConfig.RateConfig actual = rateLimiterConfig.getRateConfigFor("api_3");

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void getRateConfigFor_default_test() {
		RateLimiterConfig.RateConfig expected = RateLimiterConfig.RateConfig.builder()
				.limitRequest(BigInteger.TEN)
				.perSeconds(5)
				.waitSeconds(60)
				.build();

		RateLimiterConfig.RateConfig actual = rateLimiterConfig.getRateConfigFor("api_unknown");

		assertThat(actual).isEqualTo(expected);
	}
}