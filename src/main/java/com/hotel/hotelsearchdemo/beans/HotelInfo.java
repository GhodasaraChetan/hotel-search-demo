package com.hotel.hotelsearchdemo.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Basic data object for any Hotel information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
@JsonPropertyOrder(value = {"hotelId", "city", "room", "price"})
public class HotelInfo {
	@JsonProperty("CITY")
	private String city;
	@JsonProperty("HOTELID")
	private BigInteger hotelId;
	@JsonProperty("ROOM")
	private String room;
	@JsonProperty("PRICE")
	private BigDecimal price;
}
