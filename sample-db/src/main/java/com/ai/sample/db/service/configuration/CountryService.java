package com.ai.sample.db.service.configuration;

import java.util.List;

import com.ai.sample.common.dto.configuration.CountryDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface CountryService {

	public List<CountryDTO> findAllCountries() throws ISellDBException;

}
