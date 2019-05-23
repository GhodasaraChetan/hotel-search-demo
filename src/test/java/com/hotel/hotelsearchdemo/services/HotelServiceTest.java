package com.hotel.hotelsearchdemo.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.hotel.hotelsearchdemo.beans.HotelInfo;
import com.hotel.hotelsearchdemo.beans.HotelInfos;
import com.hotel.hotelsearchdemo.repository.HotelDataLoader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import org.junit.Test;

public class HotelServiceTest {
	private HotelService hotelService = new HotelService(new HotelDataLoader());

	@Test
	public void findByCityId_test() {

		HotelInfos expected = new HotelInfos(Arrays.asList(
				HotelInfo.builder()
						.city("Tokyo")
						.hotelId(BigInteger.valueOf(2))
						.room("Superior")
						.price(BigDecimal.valueOf(2000))
						.build(),
				HotelInfo.builder()
						.city("Tokyo")
						.hotelId(BigInteger.valueOf(3))
						.room("Sweet")
						.price(BigDecimal.valueOf(1300))
						.build()));

		HotelInfos actual = hotelService.findByCityId("Tokyo");

		assertThat(actual).containsExactlyElementsOf(expected);

	}

	@Test
	public void getAllHotels_test() {
		HotelInfos expected = new HotelInfos(Arrays.asList(
				HotelInfo.builder()
						.city("Bangkok")
						.hotelId(BigInteger.valueOf(1))
						.room("Deluxe")
						.price(BigDecimal.valueOf(1000))
						.build(),
				HotelInfo.builder()
						.city("Tokyo")
						.hotelId(BigInteger.valueOf(2))
						.room("Superior")
						.price(BigDecimal.valueOf(2000))
						.build(),
				HotelInfo.builder()
						.city("Tokyo")
						.hotelId(BigInteger.valueOf(3))
						.room("Sweet")
						.price(BigDecimal.valueOf(1300))
						.build()));

		HotelInfos actual = hotelService.getAllHotels();

		assertThat(actual).containsExactlyElementsOf(expected);

	}
}