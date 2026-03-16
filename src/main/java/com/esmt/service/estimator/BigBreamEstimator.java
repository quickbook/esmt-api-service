package com.esmt.service.estimator;
import org.springframework.stereotype.Service;

import com.esmt.enums.QuoteType;
import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;

@Service
public class BigBreamEstimator implements QuoteEstimator {

    @Override
    public QuoteType supportedType() {
        return QuoteType.FISH_BIG_BREAM_SMALL_POND;
    }

    @Override
    public QuoteEstimateResponse estimate(QuoteRequestDto request) {
        // logic
    	return null;
    }

}