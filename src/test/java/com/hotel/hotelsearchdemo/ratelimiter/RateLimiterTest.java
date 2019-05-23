package com.hotel.hotelsearchdemo.ratelimiter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotel.hotelsearchdemo.exceptions.ApiSuspendedException;
import java.math.BigInteger;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class RateLimiterTest {
	RateLimiter rateLimiter = null;

	@Before
	public void setUp() {
		Map<String, RequestRateStatus.ApiRateStatus> apiRateStatusMap = new HashMap<>();
		List<ZonedDateTime> zonedDateTimeList = new ArrayList<>();
		zonedDateTimeList.add(ZonedDateTime.of(2019, 1, 1, 1, 1, 17, 0, ZoneId.of("UTC")));
		apiRateStatusMap.put("apiKey_new_suspended",
				RequestRateStatus.ApiRateStatus.builder()
						.lastRequests(zonedDateTimeList)
						.build());
		apiRateStatusMap.put("apiKey_suspended",
				RequestRateStatus.ApiRateStatus.builder()
						.lastRequests(zonedDateTimeList)
						.isSuspended(true)
						.lastSuspendedTime(zonedDateTimeList.get(0))
						.build());

		rateLimiter = new RateLimiter(
				RateLimiterConfig.builder()
						.apiConfigs(Arrays.asList(
								RateLimiterConfig.ApiConfig.builder()
										.apiKey("apiKey")
										.apiRateConfig(RateLimiterConfig.RateConfig.builder()
												.limitRequest(BigInteger.ONE)
												.perSeconds(10)
												.waitSeconds(60)
												.build())
										.build(),
								RateLimiterConfig.ApiConfig.builder()
										.apiKey("apiKey_new_suspended")
										.apiRateConfig(RateLimiterConfig.RateConfig.builder()
												.limitRequest(BigInteger.ONE)
												.perSeconds(10)
												.waitSeconds(60)
												.build())
										.build(),
								RateLimiterConfig.ApiConfig.builder()
										.apiKey("apiKey_suspended")
										.apiRateConfig(RateLimiterConfig.RateConfig.builder()
												.limitRequest(BigInteger.ONE)
												.perSeconds(10)
												.waitSeconds(60)
												.build())
										.build(),
								RateLimiterConfig.ApiConfig.builder()
										.apiKey("apiKey_full")
										.apiRateConfig(RateLimiterConfig.RateConfig.builder()
												.limitRequest(BigInteger.ONE)
												.perSeconds(10)
												.waitSeconds(0)
												.build())
										.build()))
						.build(),
				RequestRateStatus.builder()
						.apiRateStatuses(apiRateStatusMap)
						.build());

	}

	@Test
	public void validate_no_limit_test() {
		rateLimiter.validateRateLimit("apiKey_full", ZonedDateTime.of(2019, 1, 1, 1, 1, 1, 0, ZoneId.of("UTC")));
	}

	@Test
	public void validate_empty_map_success_test() {
		rateLimiter.validateRateLimit("apiKey", ZonedDateTime.of(2019, 1, 1, 1, 1, 1, 0, ZoneId.of("UTC")));
	}

	@Test
	public void validate_new_suspend_test() {
		assertThatThrownBy(() -> {
			rateLimiter.validateRateLimit("apiKey_new_suspended", ZonedDateTime.of(2019, 1, 1, 1, 1, 20, 0, ZoneId.of("UTC")));
		}).isInstanceOf(ApiSuspendedException.class);
	}

	@Test
	public void validate_suspend_in_block_period_test() {
		assertThatThrownBy(() -> {
			rateLimiter.validateRateLimit("apiKey_suspended", ZonedDateTime.of(2019, 1, 1, 1, 1, 20, 0, ZoneId.of("UTC")));
		}).isInstanceOf(ApiSuspendedException.class);
	}

	@Test
	public void validate_suspend_out_of_block_period_test() {
		//assertThatThrownBy(() -> {
		rateLimiter.validateRateLimit("apiKey_suspended", ZonedDateTime.of(2019, 1, 1, 1, 2, 18, 0, ZoneId.of("UTC")));
		//}).isInstanceOf(ApiSuspendedException.class);
	}
}