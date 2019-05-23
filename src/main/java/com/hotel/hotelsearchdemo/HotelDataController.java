package com.hotel.hotelsearchdemo;

import com.hotel.hotelsearchdemo.beans.HotelInfos;
import com.hotel.hotelsearchdemo.beans.PriceSort;
import com.hotel.hotelsearchdemo.ratelimiter.RateLimiter;
import com.hotel.hotelsearchdemo.services.HotelService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for operating hotels information.
 */
@RestController
@AllArgsConstructor
@Slf4j
public class HotelDataController {

	private final HotelService hotelService;

	private final RateLimiter rateLimiter;

	/**
	 * Search hotels by cityId.
	 *
	 * @param cityId    for which hotels are supposed to search
	 * @param priceSort optional sort by price parameter
	 *
	 * @return
	 */
	@GetMapping("/hotels/searchByCityId")
	public HotelInfos searchByCityId(
			@RequestParam String cityId,
			@RequestParam(required = false) PriceSort priceSort,
			@RequestHeader String apiKey) {

		rateLimiter.validateRateLimit(apiKey, ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")));

		log.info("search request for " + apiKey);
		return hotelService.findByCityId(cityId).sortByPrice(priceSort);
	}
}
