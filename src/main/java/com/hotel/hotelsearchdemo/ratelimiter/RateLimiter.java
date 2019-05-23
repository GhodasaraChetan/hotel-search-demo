package com.hotel.hotelsearchdemo.ratelimiter;

import com.hotel.hotelsearchdemo.exceptions.ApiSuspendedException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RateLimiter {

	private static final String API_HAS_BEEN_SUSPENDED = "API_HAS_BEEN_SUSPENDED";
	private final RateLimiterConfig rateLimiterConfig;
	private final RequestRateStatus requestRateStatus;

	public void validateRateLimit(String apiKey, ZonedDateTime requestTime) {

		RequestRateStatus.ApiRateStatus apiRateStatus = addOrFindApiRateStatus(apiKey);
		RateLimiterConfig.RateConfig apiRateConfig = rateLimiterConfig.getRateConfigFor(apiKey);

		//waitSecond=0 represents "don't limit the rate" case.
		if (apiRateConfig.getWaitSeconds() > 0) {
			if (apiRateStatus.isSuspended()) {
				validateForAlreadySuspended(requestTime, apiRateStatus, apiRateConfig);
			} else {
				validateForNonSuspended(requestTime, apiRateStatus, apiRateConfig);
			}
		}
	}

	private void validateForNonSuspended(ZonedDateTime requestTime, RequestRateStatus.ApiRateStatus apiRateStatus, RateLimiterConfig.RateConfig apiRateConfig) {
		long requestsWithinAllowedDuration = apiRateStatus.getLastRequests().stream()
				.filter(x -> x.compareTo(requestTime.minusSeconds(apiRateConfig.getPerSeconds())) > 0)
				.count();
		if (BigInteger.valueOf(requestsWithinAllowedDuration).compareTo(apiRateConfig.getLimitRequest()) < 0) {
			//if underRated requests
			apiRateStatus.getLastRequests().add(requestTime);
		} else {
			//if overRated requests, suspend it.
			apiRateStatus.setSuspended(true);
			apiRateStatus.setLastSuspendedTime(requestTime);
			apiRateStatus.getLastRequests().add(requestTime);
			throw new ApiSuspendedException(API_HAS_BEEN_SUSPENDED,
					"API has been suspended for " + apiRateConfig.getWaitSeconds() + " seconds. Please try again later.");
		}
	}

	private void validateForAlreadySuspended(ZonedDateTime requestTime, RequestRateStatus.ApiRateStatus apiRateStatus, RateLimiterConfig.RateConfig apiRateConfig) {
		long diffSeconds = Duration.between(apiRateStatus.getLastSuspendedTime(), requestTime).getSeconds();
		if (diffSeconds < apiRateConfig.getWaitSeconds()) {
			//If request is within suspension period
			throw new ApiSuspendedException(API_HAS_BEEN_SUSPENDED,
					"API has been suspended for " + (apiRateConfig.getWaitSeconds() - diffSeconds) + " seconds. Please try again later.");
		} else {
			//If suspension period is over
			apiRateStatus.setSuspended(false);
			apiRateStatus.setLastSuspendedTime(null);
			apiRateStatus.getLastRequests().add(requestTime);
		}
	}

	private RequestRateStatus.ApiRateStatus addOrFindApiRateStatus(String apiKey) {

		if (requestRateStatus.getApiRateStatuses().containsKey(apiKey)) {
			return requestRateStatus.getApiRateStatuses().get(apiKey);
		} else {
			RequestRateStatus.ApiRateStatus apiRateStatus = RequestRateStatus.ApiRateStatus.builder()
					.isSuspended(false)
					.lastRequests(new ArrayList<>())
					.build();
			requestRateStatus.getApiRateStatuses().put(apiKey, apiRateStatus);
			return apiRateStatus;
		}
	}
}
