package com.esmt.service.estimator;

import org.springframework.stereotype.Service;

import com.esmt.dto.QuoteUIConfig;
import com.esmt.enums.QuoteType;
import com.esmt.enums.QuoteUIConfigEnum;

@Service
public class QuoteConfigService {

    public QuoteUIConfig getConfig(QuoteType type) {
        QuoteUIConfigEnum config = QuoteUIConfigEnum.valueOf(type.name());

        return new QuoteUIConfig(
                config.getTitle(),
                config.getDescription(),
                config.getInfoNotes(),
                config.getDisclaimerNotes(), null, null
        );
    }
}