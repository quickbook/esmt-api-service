package com.esmt.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.esmt.enums.QuoteType;
import com.esmt.service.estimator.QuoteEstimator;

@Component
public class QuoteEstimatorFactory {

    private final Map<QuoteType, QuoteEstimator> map;

    public QuoteEstimatorFactory(List<QuoteEstimator> estimators) {

        map = estimators.stream()
                .collect(Collectors.toMap(
                        QuoteEstimator::supportedType,
                        Function.identity()
                ));
    }

    public QuoteEstimator get(QuoteType type) {

        QuoteEstimator estimator = map.get(type);

        if (estimator == null)
            throw new RuntimeException("Estimator not found");

        return estimator;
    }

}
