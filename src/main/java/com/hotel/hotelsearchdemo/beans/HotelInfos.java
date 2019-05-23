package com.hotel.hotelsearchdemo.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

/**
 * A class which represents List of HotelInfo.
 */
@NoArgsConstructor
public class HotelInfos extends ArrayList<HotelInfo> {

	public HotelInfos(Collection<? extends HotelInfo> h) { super(h);}

	/**
	 * Sort the list of hotelInfo by price field.
	 *
	 * @param priceSort ASC/DESC/null
	 *
	 * @return sorted list of hotelInfo
	 */
	public HotelInfos sortByPrice(PriceSort priceSort) {
		if (priceSort == PriceSort.ASC) {
			return this.stream()
					.sorted(Comparator.comparing(HotelInfo::getPrice))
					.collect(Collectors.toCollection(HotelInfos::new));
		} else if (priceSort == PriceSort.DESC) {
			return this.stream()
					.sorted(Comparator.comparing(HotelInfo::getPrice).reversed())
					.collect(Collectors.toCollection(HotelInfos::new));
		}

		return this;
	}


	/**
	 * Filter the list of hotelInfo by city field.
	 *
	 * @param city cityId for which hotelInfos are supposed to filtered
	 *
	 * @return cityId filtered list of hotelInfo
	 */
	public HotelInfos filterByCity(String city) {
		return this.stream()
				.filter(hotelInfo -> hotelInfo.getCity().equals(city))
				.collect(Collectors.toCollection(HotelInfos::new));
	}
}
