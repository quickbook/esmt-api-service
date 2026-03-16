package com.esmt.service.estimator;

import com.esmt.enums.QuoteType;
import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;

public interface QuoteEstimator {

    QuoteType supportedType();

    QuoteEstimateResponse estimate(QuoteRequestDto request);
}