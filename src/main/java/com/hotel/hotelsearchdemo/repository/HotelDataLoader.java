package com.hotel.hotelsearchdemo.repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.hotel.hotelsearchdemo.beans.HotelInfo;
import com.hotel.hotelsearchdemo.beans.HotelInfos;
import com.hotel.hotelsearchdemo.exceptions.CsvFileReadException;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Repository to communicate with Hotel info data.
 */
@Component
public class HotelDataLoader {

	private static final String HOTEL_INFO_FILE = "/hoteldb.csv";
	private static final String DATA_LOAD_FROM_CSV_FAILED = "DATA_LOAD_FROM_CSV_FAILED";

	/**
	 * Read hoteldb.csv and load all hotelInfos.
	 *
	 * @return All hotelInfos from csv (if not error) or empty collection (if any exception).
	 */
	public HotelInfos loadHotelInfosFromCsv() {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
		try {
			File file = new ClassPathResource(HOTEL_INFO_FILE).getFile();
			MappingIterator<HotelInfo> readValues = csvMapper.readerFor(HotelInfo.class).with(csvSchema).readValues(file);
			return new HotelInfos(readValues.readAll());
		} catch (IOException e) {
			throw new CsvFileReadException(DATA_LOAD_FROM_CSV_FAILED,
					"Error occurred while loading data from csv file. Exception: " + e.getMessage());
		}
	}
}
