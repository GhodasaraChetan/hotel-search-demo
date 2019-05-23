package com.hotel.hotelsearchdemo.beans;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class HotelInfosTest {

	private HotelInfos hotelInfos = new HotelInfos();

	@Before
	public void setUp() {
		hotelInfos.add(HotelInfo.builder()
				.hotelId(BigInteger.valueOf(1))
				.city("Bangkok")
				.room("Deluxe")
				.price(new BigDecimal(200))
				.build());
		hotelInfos.add(HotelInfo.builder()
				.hotelId(BigInteger.valueOf(2))
				.city("Tokyo")
				.room("Superior")
				.price(new BigDecimal(300))
				.build());
		hotelInfos.add(HotelInfo.builder()
				.hotelId(BigInteger.valueOf(3))
				.city("Bangkok")
				.room("Deluxe")
				.price(new BigDecimal(100))
				.build());
	}

	@Test
	public void sortByPrice_asc_test() {
		HotelInfos expected = new HotelInfos(Arrays.asList(
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(3))
						.city("Bangkok")
						.room("Deluxe")
						.price(new BigDecimal(100))
						.build(),
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(1))
						.city("Bangkok")
						.room("Deluxe")
						.price(new BigDecimal(200))
						.build(),
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(2))
						.city("Tokyo")
						.room("Superior")
						.price(new BigDecimal(300))
						.build()
		));

		HotelInfos actual = this.hotelInfos.sortByPrice(PriceSort.ASC);

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	public void sortByPrice_desc_test() {
		HotelInfos expected = new HotelInfos(Arrays.asList(
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(2))
						.city("Tokyo")
						.room("Superior")
						.price(new BigDecimal(300))
						.build(),
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(1))
						.city("Bangkok")
						.room("Deluxe")
						.price(new BigDecimal(200))
						.build(),
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(3))
						.city("Bangkok")
						.room("Deluxe")
						.price(new BigDecimal(100))
						.build()
		));

		HotelInfos actual = this.hotelInfos.sortByPrice(PriceSort.DESC);

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	public void sortByPrice_null_test() {
		HotelInfos expected = new HotelInfos(Arrays.asList(
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(1))
						.city("Bangkok")
						.room("Deluxe")
						.price(new BigDecimal(200))
						.build(),
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(2))
						.city("Tokyo")
						.room("Superior")
						.price(new BigDecimal(300))
						.build(),
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(3))
						.city("Bangkok")
						.room("Deluxe")
						.price(new BigDecimal(100))
						.build()
		));

		HotelInfos actual = this.hotelInfos.sortByPrice(null);

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	public void filterByCity_test() {
		HotelInfos expected = new HotelInfos(Arrays.asList(
				HotelInfo.builder()
						.hotelId(BigInteger.valueOf(2))
						.city("Tokyo")
						.room("Superior")
						.price(new BigDecimal(300))
						.build()
		));

		HotelInfos actual = this.hotelInfos.filterByCity("Tokyo");

		assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
	}


}