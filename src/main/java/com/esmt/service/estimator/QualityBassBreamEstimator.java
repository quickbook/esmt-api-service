package com.esmt.service.estimator;
import org.springframework.stereotype.Service;

import com.esmt.enums.QuoteType;
import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;

@Service
public class QualityBassBreamEstimator implements QuoteEstimator {

    @Override
    public QuoteType supportedType() {
        return QuoteType.FISH_QUALITY_BASS_BREAM;
    }

    @Override
    public QuoteEstimateResponse estimate(QuoteRequestDto request) {
        // logic
    	return null;
    }

}
