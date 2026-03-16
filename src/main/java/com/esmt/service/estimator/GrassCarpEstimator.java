package com.esmt.service.estimator;

import org.springframework.stereotype.Service;

import com.esmt.enums.QuoteType;
import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;

@Service
public class GrassCarpEstimator implements QuoteEstimator {

    @Override
    public QuoteType supportedType() {
        return QuoteType.STOCK_GRASS_CARP;
    }

    @Override
    public QuoteEstimateResponse estimate(QuoteRequestDto request) {
        // logic
    	return null;
    }

}
