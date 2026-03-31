package com.esmt.service.estimator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmt.dto.QuotePackageOption;
import com.esmt.dto.QuoteUIConfig;
import com.esmt.enums.QuoteType;
import com.esmt.repository.DistancePricingRepository;
import com.esmt.repository.FishPriceRepository;
import com.esmt.repository.PackageFishCompositionRepository;
import com.esmt.repository.QuotePackageRepository;
import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;
import com.esmt.service.AddonService;

@Service
public class BigBreamEstimator extends BaseNewPondQuoteEstimator {
	
	@Autowired
	QuoteConfigService configService;

	protected BigBreamEstimator(QuotePackageRepository packageRepo, PackageFishCompositionRepository compositionRepo,
			FishPriceRepository priceRepo, DistancePricingRepository distanceRepo, AddonService addonService) {
		super(packageRepo, compositionRepo, priceRepo, distanceRepo, addonService);
	 
	} 

 
	@Override
	protected QuoteEstimateResponse buildQuote(QuoteRequestDto request, List<QuotePackageOption> result) {
		
		QuoteUIConfig config = configService.getConfig(request.getQuoteType());

	    return baseResponse(request, result)
	            .title(config.getTitle())
	            .description(config.getDescription())
	            .infoNotes(config.getInfoNotes())
	            .disclaimerNotes(config.getDisclaimerNotes())
	    		.message("Quote estimation successful.")
	            .build();

		 
	}

    @Override
    public QuoteType supportedType() {
        return QuoteType.FISH_BIG_BREAM_SMALL_POND;
    }

    

}