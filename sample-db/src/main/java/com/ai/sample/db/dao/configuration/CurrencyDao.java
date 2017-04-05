package com.ai.sample.db.dao.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.Currency;

public interface CurrencyDao {

	void saveCurrency(Currency currency) throws ISellDBException;
	
	List<Currency> findAllCurrencies() throws ISellDBException;
	
	Currency findCurrencyByName(String currencyName) throws ISellDBException;
	
	void deleteCurrencyByName(String currencyName) throws ISellDBException;
	
	Currency getOrCreateCurrencyByName(String currencyName) throws ISellDBException;

}
