package com.hotel.hotelsearchdemo.services;

import com.hotel.hotelsearchdemo.beans.HotelInfos;
import com.hotel.hotelsearchdemo.repository.HotelDataLoader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for hotel data requests.
 */
@AllArgsConstructor
@Service
public class HotelService {

	private HotelDataLoader hotelDataLoader;

	/**
	 * Search hotels from cityId.
	 *
	 * @param city cityId for which hotels are supposed to search
	 *
	 * @return HotelInfos whose city is matching with requested cityId
	 */
	public HotelInfos findByCityId(String city) {
		return getAllHotels().filterByCity(city);
	}

	/**
	 * Get all hotels data.
	 *
	 * @return HotelInfos with all hotels in data repository
	 */
	public HotelInfos getAllHotels() {
		return hotelDataLoader.loadHotelInfosFromCsv();
	}
}
