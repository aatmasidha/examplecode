package com.ai.sample.dataservices.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDTO;
import com.ai.sample.common.dto.ui.ExecutionStatusDTO;
import com.ai.sample.common.dto.ui.RateShoppingPropertyDetailsJsonDTO;
import com.ai.sample.common.dto.ui.RateShoppingPropertyDetailsResponseJsonDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.RateShoppingPropertyDetailsService;

@RestController
@RequestMapping("/rateshopping/")
public class RateShoppingPropertyServicesRestController {

	static final Logger logger = Logger.getLogger(RateShoppingPropertyServicesRestController.class);
	@RequestMapping("/getallunmappedproperties/")
	public RateShoppingPropertyDetailsResponseJsonDTO getAllUnmappedProperties() throws Exception{//REST Endpoint.
		RateShoppingPropertyDetailsResponseJsonDTO rateShoppingPropertyDetailsResponseJsonDTO = 
				new RateShoppingPropertyDetailsResponseJsonDTO();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");

			List<RateShoppingPropertyDetailsJsonDTO> rateShoppingPropertyDetailsJsonDTOList =  new ArrayList<RateShoppingPropertyDetailsJsonDTO>();
			RateShoppingPropertyDetailsService rateShoppingPropertyDetailsService = context.getBean(RateShoppingPropertyDetailsService.class);

			List<RateShoppingPropertyDTO>  rateShoppingUnmappedPropertyDetailsList = rateShoppingPropertyDetailsService.findRateShoppingUnmappedProperties();
			for(final RateShoppingPropertyDTO rateShoppingPropertyDetailsDTO : rateShoppingUnmappedPropertyDetailsList)
			{
				RateShoppingPropertyDetailsJsonDTO rateShoppingPropertyDetailsJsonDTO
				= new RateShoppingPropertyDetailsJsonDTO(rateShoppingPropertyDetailsDTO.getId(), rateShoppingPropertyDetailsDTO.getName(),
						rateShoppingPropertyDetailsDTO.getPropertyCode(), rateShoppingPropertyDetailsDTO.getHotelChainName(), 
						rateShoppingPropertyDetailsDTO.getAddress(), rateShoppingPropertyDetailsDTO.getCountry(), rateShoppingPropertyDetailsDTO.getState(), 
						rateShoppingPropertyDetailsDTO.getCity(), rateShoppingPropertyDetailsDTO.getPinCode(), rateShoppingPropertyDetailsDTO.getCapcity(), 
						rateShoppingPropertyDetailsDTO.getRanking(), rateShoppingPropertyDetailsDTO.getPropertyStatus(), rateShoppingPropertyDetailsDTO.getHotelCurrency(), 
						rateShoppingPropertyDetailsDTO.getLongitude(), rateShoppingPropertyDetailsDTO.getLatitude());
				rateShoppingPropertyDetailsJsonDTOList.add(rateShoppingPropertyDetailsJsonDTO);
			}
			rateShoppingPropertyDetailsResponseJsonDTO.setRateShoppingPropertyDetailsJsonDTO(rateShoppingPropertyDetailsJsonDTOList);
			
			((AbstractApplicationContext)context).close();
			
			ExecutionStatusDTO executionStatusDTO = rateShoppingPropertyDetailsResponseJsonDTO.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(0);
			executionStatusDTO.setStatusDescription("SUCCESS");
			return rateShoppingPropertyDetailsResponseJsonDTO;
			
		} catch (ISellDBException e) {
			logger.error("Exception while fetching unmapped rateshopping properties. ");
			logger.error("Exception is: " + e.getMessage());
			ExecutionStatusDTO executionStatusDTO = rateShoppingPropertyDetailsResponseJsonDTO.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(e.getErrorCode());
			executionStatusDTO.setStatusDescription("FAILURE while getting unmapped rate shopping properties: " + e.getMessage());
			return rateShoppingPropertyDetailsResponseJsonDTO;
		}
	}
}
