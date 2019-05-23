package com.hotel.hotelsearchdemo.ratelimiter;

import java.math.BigInteger;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration bean defined for setting rateLimits.
 */
@Component
@ConfigurationProperties(prefix = "rate-limiter-config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateLimiterConfig {

	private List<ApiConfig> apiConfigs;
	private RateConfig defaultRateConfig;

	/**
	 * Get rateConfig for given ApiKey if found. otherwise return default rateConfig.
	 *
	 * @param apiKey for which rateConfig has been asked.
	 *
	 * @return api's rateConfig if present otherwise default rateConfig
	 */
	public RateConfig getRateConfigFor(String apiKey) {
		return this.apiConfigs.stream()
				.filter(config -> config.getApiKey().equals(apiKey))
				.map(ApiConfig::getApiRateConfig)
				.findFirst()
				.orElse(this.defaultRateConfig);
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ApiConfig {
		private String apiKey;
		private RateConfig apiRateConfig;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RateConfig {
		private BigInteger limitRequest;
		private long perSeconds;
		private long waitSeconds;
	}
}
