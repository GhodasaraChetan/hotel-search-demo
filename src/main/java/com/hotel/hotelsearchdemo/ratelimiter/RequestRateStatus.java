package com.hotel.hotelsearchdemo.ratelimiter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * RequestRateStatus singleton object to maintain the status of each API's request rate.
 */
@Data
@Builder
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RequestRateStatus {

	private Map<String, ApiRateStatus> apiRateStatuses = new HashMap<>();

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	static class ApiRateStatus {
		private boolean isSuspended;
		@Builder.Default
		private List<ZonedDateTime> lastRequests = new ArrayList<>();
		private ZonedDateTime lastSuspendedTime;
	}
}
